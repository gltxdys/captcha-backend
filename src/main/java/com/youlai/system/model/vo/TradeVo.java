package com.youlai.system.model.vo;

import com.youlai.system.model.dto.PreCreateTo;

public class TradeVo extends PreCreateTo {
    public TradeVo(PreCreateTo pre, String qrCode){
        this.setQrCode(qrCode);
    }
}
