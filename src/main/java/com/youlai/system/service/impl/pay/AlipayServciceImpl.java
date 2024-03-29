package com.youlai.system.service.impl.pay;

import cn.hutool.core.lang.UUID;
import com.alipay.v3.ApiClient;
import com.alipay.v3.ApiException;
import com.alipay.v3.Configuration;
import com.alipay.v3.api.AlipayTradeApi;
import com.alipay.v3.model.*;
import com.alipay.v3.util.model.AlipayConfig;
import com.youlai.system.model.dto.PreCreateTo;
import com.youlai.system.service.payService;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConditionalOnProperty(value = "pay.type", havingValue = "alipay")
@ConfigurationProperties(prefix = "pay.alipay")
@RequiredArgsConstructor
@Data
@Slf4j
public class AlipayServciceImpl implements payService {

    private String path;
    private String appId;
    private String privateKey;
    private String alipayPubkey;
    private String encryptKey;
    private ApiClient appClient;
    private AlipayTradeApi api;
    public static String CALL_BACK_URL = "http://tw.2007nb.cf:9002/a/b";

    @SneakyThrows
    @PostConstruct
    public void init() {
        appClient = Configuration.getDefaultApiClient();
        appClient.setBasePath(path);
        AlipayConfig config = new AlipayConfig();
        config.setAppId(appId);
        config.setPrivateKey(privateKey);
        config.setAlipayPublicKey(alipayPubkey);
        if (ObjectUtils.isNotEmpty(encryptKey)) config.setEncryptKey(encryptKey);
        appClient.setAlipayConfig(config);
        api = new AlipayTradeApi(appClient);
    }

    public PreCreateTo preCreate(Integer amount) {
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        String orderNo = UUID.fastUUID().toString(true);
        model.setOutTradeNo(orderNo);
        model.setSubject("lovejj" + System.currentTimeMillis());
        model.setTotalAmount(amount.toString());
        model.setTimeoutExpress("15m");
        model.setNotifyUrl(CALL_BACK_URL); // 异步回调通知地址
        AlipayTradePrecreateResponseModel repModel = null;
        PreCreateTo preCreateTo=new PreCreateTo();
        try {
            repModel = api.precreate(model);
            log.info("创建预付款二维码：{}",repModel.toJson());
            //TODO 插入订单表
            preCreateTo.setAmount(amount);
            preCreateTo.setOrderId(orderNo);
            preCreateTo.setTradeNo(repModel.getOutTradeNo());
            preCreateTo.setQrCode(repModel.getQrCode());
        } catch (ApiException e) {
            //TODO 更改订单状态
            AlipayTradePayDefaultResponse errorObject = (AlipayTradePayDefaultResponse) e.getErrorObject();
            if (errorObject != null && errorObject.getActualInstance() instanceof CommonErrorType) {
                //获取公共错误码
                CommonErrorType actualInstance = errorObject.getCommonErrorType();
                System.out.println("CommonErrorType:" + actualInstance.toString());
            } else if (errorObject != null && errorObject.getActualInstance() instanceof AlipayTradePayErrorResponseModel) {
                //获取业务错误码
                AlipayTradePayErrorResponseModel actualInstance = errorObject.getAlipayTradePayErrorResponseModel();
                System.out.println("AlipayTradePayErrorResponseModel:" + actualInstance.toString());
            } else {
                //获取其他报错（如加验签失败、http请求失败等）
                System.err.println("Status code: " + e.getCode());
                System.err.println("Reason: " + e.getResponseBody());
                System.err.println("Response headers: " + e.getResponseHeaders());
                System.out.println(e);
            }
        }
        return preCreateTo;
    }

    @Override
    public void query() {

    }

    @Override
    public void cancel() {

    }

}
