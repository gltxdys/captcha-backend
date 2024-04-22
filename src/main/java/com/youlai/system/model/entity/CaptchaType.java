package com.youlai.system.model.entity;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.youlai.system.common.base.BaseEntity;
import com.youlai.system.model.vo.CaptchaDetailVo;
import com.youlai.system.model.vo.CaptchaTypeVo;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@ToString
@Accessors(chain = true)
public class CaptchaType extends BaseEntity {
    @TableId(type = IdType.AUTO)
    @TableField("type_id")
    private Long typeId;
    private String typeName;
    private String details;
    private Integer price;
    private String reqParam;
    private String repParam;
    private String exampleUrl;

    public static CaptchaType transToCaptchaType(CaptchaDetailVo captchaTypeVo){
        CaptchaType captchaType = new CaptchaType();
        captchaType.setTypeId(captchaTypeVo.getTypeId());
        captchaType.setTypeName(captchaTypeVo.getTypeName());
        captchaType.setDetails(captchaTypeVo.getDetails());
        captchaType.setPrice(captchaTypeVo.getPrice());
        if(!captchaTypeVo.getExampleUrl().isEmpty()){
            if(captchaTypeVo.getExampleUrl().size()>1)
                captchaType.setExampleUrl(StrUtil.join("|",captchaTypeVo.getExampleUrl()));
            else
                captchaType.setExampleUrl(captchaTypeVo.getExampleUrl().get(0));
        }
        captchaType.setReqParam(JSONUtil.toJsonStr(captchaTypeVo.getReqParam()));
        return captchaType;
    }
}
