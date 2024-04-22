package com.youlai.system.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Schema(description ="邮箱验证码响应对象")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailCaptchaResult {

    @Schema(description = "验证码缓存key")
    private String emailKey;

}
