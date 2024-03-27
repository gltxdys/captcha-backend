package com.youlai.system.controller;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.youlai.system.common.result.Result;
import com.youlai.system.model.dto.PreCreateTo;
import com.youlai.system.model.vo.TradeVo;
import com.youlai.system.service.payService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static cn.hutool.extra.qrcode.QrCodeUtil.QR_TYPE_SVG;

@RestController
@RequestMapping("/api/v1/pay")
public class PayController {
    @Autowired
    payService payService;

    @GetMapping("")
    public Result<TradeVo> preCreate(@RequestParam("money") Integer money){
        PreCreateTo preCreateTo = payService.preCreate(money);
        QrConfig config = new QrConfig(300, 300);
        //Base64 QrCode
        String qrCode = QrCodeUtil.generateAsBase64(preCreateTo.getQrCode(), config, QR_TYPE_SVG);
        TradeVo tradeVo=new TradeVo(preCreateTo,qrCode);
        return Result.success(tradeVo);
    }

}
