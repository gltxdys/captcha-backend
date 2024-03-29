package com.youlai.system.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class IdentifyVo {
    private String picUrl;
    private String result;
}
