package com.youlai.system.security.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.youlai.system.security.constant.JwtClaimConstants;
import com.youlai.system.security.model.SysUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * JWT 工具类
 *
 * @author haoxr
 * @since 2.6.0
 */
@Component
public class JwtUtils {

    /**
     * JWT 加解密使用的密钥
     */
    private static byte[] key;


    /**
     * JWT Token 的有效时间(单位:秒)
     */
    private static int ttl;


    @Value("${jwt.key}")
    public void setKey(String key) {
        JwtUtils.key = key.getBytes();
    }

    @Value("${jwt.ttl}")
    public void setTtl(Integer ttl) {
        JwtUtils.ttl = ttl;
    }

    /**
     * 生成 JWT Token
     *
     * @param authentication 用户认证信息
     * @return Token 字符串
     */
    public static String generateToken(Authentication authentication) {

        SysUserDetails userDetails = (SysUserDetails) authentication.getPrincipal();

        Map<String, Object> payload = new HashMap<>();
        payload.put(JwtClaimConstants.USER_ID, userDetails.getUserId()); // 用户ID
        payload.put(JwtClaimConstants.DEPT_ID, userDetails.getDeptId()); // 部门ID
        payload.put(JwtClaimConstants.DATA_SCOPE, userDetails.getDataScope()); // 数据权限范围

        // claims 中添加角色信息
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        payload.put(JwtClaimConstants.AUTHORITIES, roles);


        Date now = new Date();
        Date expiration = DateUtil.offsetSecond(now, ttl);
        payload.put(JWTPayload.ISSUED_AT, now);
        payload.put(JWTPayload.EXPIRES_AT, expiration);
        payload.put(JWTPayload.SUBJECT, authentication.getName());
        payload.put(JWTPayload.JWT_ID, IdUtil.simpleUUID());

        return JWTUtil.createToken(payload, JwtUtils.key);
    }


    /**
     * 从 JWT Token 中解析 Authentication  用户认证信息
     *
     * @param payload JWT 载体
     * @return 用户认证信息
     */
    public static UsernamePasswordAuthenticationToken getAuthentication(Map<String, Object> payload) {
        SysUserDetails userDetails = new SysUserDetails();
        userDetails.setUserId(Convert.toLong(payload.get(JwtClaimConstants.USER_ID))); // 用户ID
        userDetails.setDeptId(Convert.toLong(payload.get(JwtClaimConstants.DEPT_ID))); // 部门ID
        userDetails.setDataScope(Convert.toInt(payload.get(JwtClaimConstants.DATA_SCOPE))); // 数据权限范围

        userDetails.setUsername(Convert.toStr(payload.get(JWTPayload.SUBJECT))); // 用户名
        // 角色集合
        Set<SimpleGrantedAuthority> authorities = ((JSONArray) payload.get(JwtClaimConstants.AUTHORITIES))
                .stream()
                .map(authority -> new SimpleGrantedAuthority(Convert.toStr(authority)))
                .collect(Collectors.toSet());

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }


    /**
     * 解析 JWT Token 获取载体信息
     *
     * @param token JWT Token
     * @return 载体信息
     */
    public static Map<String, Object> parseToken(String token) {
        try {
            if (StrUtil.isBlank(token)) {
                return null;
            }

            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            JWT jwt = JWTUtil.parseToken(token);
            if (jwt.setKey(JwtUtils.key).validate(0)) {
                return jwt.getPayloads();
            }
        } catch (Exception ignored) {
        }
        return null;
    }


}
