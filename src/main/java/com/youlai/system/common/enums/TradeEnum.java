package com.youlai.system.common.enums;

import com.youlai.system.common.base.IBaseEnum;
import lombok.Getter;

public enum  TradeEnum implements IBaseEnum<Integer> {
    CREATE(0, "create"),
    FAIL(1, "TRADE_FAIL"),
    FINISH(3, "TRADE_FINISH"),
    SUCCESS (2, "TRADE_SUCCESS");

    @Getter
    private Integer value;

    @Getter
    private String label;

    TradeEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
