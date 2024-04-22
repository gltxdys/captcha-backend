package com.youlai.system.service.impl.pay;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import com.alipay.v3.ApiClient;
import com.alipay.v3.ApiException;
import com.alipay.v3.Configuration;
import com.alipay.v3.api.AlipayTradeApi;
import com.alipay.v3.model.*;
import com.alipay.v3.util.model.AlipayConfig;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.youlai.system.common.enums.PayEnum;
import com.youlai.system.common.enums.TradeEnum;
import com.youlai.system.model.dto.PreCreateTo;
import com.youlai.system.model.entity.Trade;
import com.youlai.system.security.util.SecurityUtils;
import com.youlai.system.service.PayService;
import com.youlai.system.service.TradeService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.util.*;


@Component
@ConditionalOnProperty(value = "pay.type", havingValue = "alipay")
@ConfigurationProperties(prefix = "pay.alipay")
@RequiredArgsConstructor
@Data
@Slf4j
public class AlipayServciceImpl implements PayService {

    private String path;
    private String appId;
    private String privateKey;
    private String alipayPubkey;
    private String encryptKey;
    private ApiClient appClient;
    private AlipayTradeApi api;
    public static String CALL_BACK_URL = "http://captcha.2007nb.cf:8081/api/v1/pay/callback";
    public static String pubkey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAy0QmW+f88keYruK4sAbz71pVNnSXMwH+D9TlDF29qIi2qTwMRJwcvmt/Bw3764RD/H4439oaP1aAcyCtca4CyfZeHXyHEOk7+aIqVTdQrxg0eQYzALx1xbkvrVQnv5SXARXAxt9Zcc6s9RQscmOoIkrGpwGrQzqltRbebM7gOfLWYg3oRqWe2rOpBmlNatHF/LuQ8m+IrOSa36qpfWuR0bkQSwpmASUEPNZ+6QCfV1mgWDMjzdHMLf0dNQ0p//APQ1i657Fw/WT410SSh46kpxJMHcBK16XFO/0y/7fJKER/y4U4jyq1sU/CD8o6Y1hwFEIKzQo2gvQpucLwWWcfiwIDAQAB";

    @Autowired
    private  TradeService tradeService;

    @Autowired
    private RedissonClient redissonClient;

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
        Long orderNo = IdUtil.getSnowflakeNextId();
        model.setOutTradeNo(orderNo.toString());
        model.setSubject("lovejj" + System.currentTimeMillis());
        model.setTotalAmount(amount.toString());
        model.setTimeoutExpress("15m");
        model.setNotifyUrl(CALL_BACK_URL); // 异步回调通知地址
        AlipayTradePrecreateResponseModel repModel = null;
        PreCreateTo preCreateTo=new PreCreateTo();
        Trade trade =new Trade();
        trade.setMoney(amount.doubleValue()).setTradeNo(orderNo).setScore(amount*10)
                .setPayType(PayEnum.ALIPAY.getValue()).setUserId(SecurityUtils.getUserId().intValue());
        try {
            repModel = api.precreate(model);
            log.info("创建预付款二维码：{}",repModel.toJson());
            tradeService.save(trade.setStatus(1));
            redissonClient.getBucket(orderNo.toString()).setIfAbsent(trade, Duration.ofMinutes(15));
            preCreateTo.setAmount(amount);
            preCreateTo.setOrderId(orderNo.toString());
            preCreateTo.setTradeNo(repModel.getOutTradeNo());
            preCreateTo.setQrCode(repModel.getQrCode());
        } catch (ApiException e) {
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

    public String getSignCheckContent(Map<String, String> params) {
        if (params == null) {
            return null;
        }

        StringBuilder content = new StringBuilder();
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            content.append(i == 0 ? "" : "&").append(key).append("=").append(value);
        }
        return content.toString();
    }

    public boolean verifyCallback(String content,String sign,String publicKey) throws Exception {
        try {
            byte[] encodedKey = publicKey.getBytes();
            encodedKey = Base64.getDecoder().decode(encodedKey);
            PublicKey pubKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(encodedKey));

            java.security.Signature signature = java.security.Signature.getInstance("SHA256withRSA");
            signature.initVerify(pubKey);
            signature.update(content.getBytes(StandardCharsets.UTF_8));

            return signature.verify(Base64.getDecoder().decode(sign.getBytes()));

        } catch (Exception e) {
            String errorMessage = "验签失败，请检查公钥格式是否正确。content=" + content + " publicKey=" + publicKey + " reason="
                    + e.getMessage();
            throw new Exception(errorMessage);
        }
    }
    public  boolean alipayCallback(HttpServletRequest request) throws Exception {
        // 获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, new String(valueStr.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        }
        String sign = params.get("sign");
        String tradeNo = params.get("out_trade_no");
        String amount = params.get("total_amount");
        String status = params.get("trade_status");
        params.remove("sign");
        params.remove("sign_type");
        boolean result = verifyCallback(getSignCheckContent(params), sign,alipayPubkey );
        if (!result|| !Objects.equals(status, TradeEnum.SUCCESS.getLabel())){
            return false;
        }
        RBucket<Object> bucket = redissonClient.getBucket(tradeNo);
        if(bucket.isExists()){
            Trade trade = (Trade) bucket.get();
            if(trade.getMoney()!=Double.parseDouble(amount))return false;
            tradeService.tradeSuccess(trade);
            bucket.delete();

            return true;
        }
        return false;
    }

    @Override
    public void query() {

    }

    @Override
    public void cancel() {

    }

}
