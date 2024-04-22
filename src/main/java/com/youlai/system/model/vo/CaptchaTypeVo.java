package com.youlai.system.model.vo;

import cn.hutool.core.util.StrUtil;
import com.youlai.system.model.entity.CaptchaType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class CaptchaTypeVo {
    private Long typeId;
    private String typeName;
    private String details;
    private Integer price;
    private List<String> exampleUrl;

    public CaptchaTypeVo(CaptchaType captchaType) {
        this.typeId = captchaType.getTypeId();
        this.typeName = captchaType.getTypeName();
        this.details = captchaType.getDetails();
        this.price = captchaType.getPrice();
        if(StrUtil.isNotBlank(captchaType.getExampleUrl())){
            this.exampleUrl = List.of(captchaType.getExampleUrl().split("\\|"));
        }
    }
}
