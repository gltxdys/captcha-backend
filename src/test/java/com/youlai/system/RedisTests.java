package com.youlai.system;

import com.youlai.system.model.entity.SysUser;
import com.youlai.system.service.CaptchaTypeService;
import com.youlai.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Redis 单元测试
 *
 * @author haoxr
 * @since 2023/02/17
 */
@SpringBootTest
@Slf4j
public class RedisTests {

    @Autowired
    private  RedisTemplate redisTemplate;

    @Autowired
    private CaptchaTypeService captchaTypeService;

    /**
     * Redis 序列化测试
     */
    @Test
    public void testRedisSerializer() {
        SysUser user = new SysUser();
        user.setId(1l);
        user.setNickname("张三");
        // 写
        redisTemplate.opsForValue().set("user", user);

        // 读
        SysUser userCache = (SysUser)redisTemplate.opsForValue().get("user");
        log.info("userCache:{}", userCache);

    }
    @Test
    public void testCaptchaType() {
        System.out.println(captchaTypeService.getAllCaptchaTypeVoList());
    }

}
