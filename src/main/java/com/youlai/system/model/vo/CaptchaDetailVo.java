package com.youlai.system.model.vo;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.youlai.system.model.entity.CaptchaType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class CaptchaDetailVo {
    private Long typeId;
    private String typeName;
    private String details;
    private Integer price;
    private List<ParamVo> reqParam;
    private List<ParamVo> repParam;
    private List<String> exampleUrl;

    public CaptchaDetailVo(CaptchaType captcha) {
        this.typeId = captcha.getTypeId();
        this.typeName = captcha.getTypeName();
        this.details = captcha.getDetails();
        this.price = captcha.getPrice();
        if (StrUtil.isNotBlank(captcha.getExampleUrl())) {
            this.exampleUrl = List.of(captcha.getExampleUrl().split("\\|"));
        }
        if(StrUtil.isNotBlank(captcha.getReqParam())) {
            this.reqParam = JSONUtil.toList(captcha.getReqParam(),ParamVo.class);
        }
        if(StrUtil.isNotBlank(captcha.getRepParam())) {
            this.repParam = JSONUtil.toList(captcha.getRepParam(),ParamVo.class);
        }
    }
}
