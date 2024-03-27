package com.youlai.system.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PreCreateTo {
    private Integer amount;
    private String orderId;
    private String tradeNo;
    private String qrCode;

}
