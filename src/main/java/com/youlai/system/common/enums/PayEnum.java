package com.youlai.system.common.enums;

import com.youlai.system.common.base.IBaseEnum;
import lombok.Getter;

public enum  PayEnum implements IBaseEnum<Integer> {
    ALIPAY(0, "Alipay"),
    WECHAT (1, "WeChat");

    @Getter
    private Integer value;

    @Getter
    private String label;

    PayEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
