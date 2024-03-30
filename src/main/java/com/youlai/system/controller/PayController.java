package com.youlai.system.controller;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.youlai.system.common.enums.TradeEnum;
import com.youlai.system.common.result.Result;
import com.youlai.system.model.dto.PreCreateTo;
import com.youlai.system.model.entity.Trade;
import com.youlai.system.model.vo.TradeVo;
import com.youlai.system.service.PayService;
import com.youlai.system.service.TradeService;
import com.youlai.system.service.impl.pay.AlipayServciceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

import static cn.hutool.extra.qrcode.QrCodeUtil.QR_TYPE_SVG;

@Slf4j
@RestController
@RequestMapping("/api/v1/pay")
public class PayController {
    @Autowired
    AlipayServciceImpl payService;

    @Autowired
    TradeService tradeService;

    @GetMapping("/{money}")
    public Result<TradeVo> preCreate(@PathVariable Integer money){
        PreCreateTo preCreateTo = payService.preCreate(money);
        QrConfig config = new QrConfig(100, 100);
        config.setErrorCorrection(ErrorCorrectionLevel.M);
        //Base64 QrCode
        String qrCode = QrCodeUtil.generateAsBase64(preCreateTo.getQrCode(), config, QR_TYPE_SVG);
        TradeVo tradeVo=new TradeVo(preCreateTo,qrCode);
        return Result.success(tradeVo);
    }

    @GetMapping("/getTradeResult")
    public Result<Boolean> getTradeResult(@RequestParam("tradeNo") String tradeNo){
        Optional<Trade> optById = tradeService.getOptById(tradeNo);
        return Result.success(optById.isPresent()&&optById.get().getStatus()== TradeEnum.SUCCESS.getValue());
    }
    @PostMapping("/callback")
    public String test(HttpServletRequest request) throws Exception {
        log.info(request.toString());
        boolean success= payService.alipayCallback(request);
        return success?"success":"fail";
    }

}
