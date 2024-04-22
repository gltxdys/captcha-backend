package com.youlai.system.controller;

import com.youlai.system.common.result.Result;
import com.youlai.system.model.dto.CaptchaResult;
import com.youlai.system.model.dto.EmailCaptchaResult;
import com.youlai.system.model.dto.LoginResult;
import com.youlai.system.security.util.SecurityUtils;
import com.youlai.system.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Objects;

@Tag(name = "01.认证中心")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final RedissonClient redissonClient;

    @Operation(summary = "登录")
    @PostMapping("/login")
    public Result<LoginResult> login(
            @Parameter(description = "用户名", example = "admin") @RequestParam String username,
            @Parameter(description = "密码", example = "123456") @RequestParam String password
    ) {
        LoginResult loginResult = authService.login(username, password);
        return Result.success(loginResult);
    }

    @Operation(summary = "注销")
    @DeleteMapping("/logout")
    public Result logout() {
        authService.logout();
        return Result.success();
    }

    @Operation(summary = "获取验证码")
    @GetMapping("/captcha")
    public Result<CaptchaResult> getCaptcha() {
        CaptchaResult captcha = authService.getCaptcha();
        return Result.success(captcha);
    }

    @Operation(summary = "获取邮箱验证码")
    @GetMapping("/email")
    public Result<EmailCaptchaResult> getEmailCaptcha() {
        String email = Objects.requireNonNull(SecurityUtils.getUser()).getEmail();
        EmailCaptchaResult captcha = authService.getEmailCaptcha(email);
        return Result.success(captcha);
    }

    @Operation(summary = "获取新邮箱验证码")
    @GetMapping("/new/email")
    public Result<EmailCaptchaResult> getNewEmailCaptcha(@RequestParam String email) {
        EmailCaptchaResult captcha = authService.getEmailCaptcha(email);
        redissonClient.getBucket(captcha.getEmailKey()+":email").set(email, Duration.ofMinutes(1));
        return Result.success(captcha);
    }
}
