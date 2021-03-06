/*
 * Copyright (c) 2020. 布比（北京）网络技术有限公司.
 * All rights reserved.
 */

package cn.prosayj.authentication.csrf.util.jwt;

import cn.prosayj.authentication.csrf.model.user.CustomUserDetails;
import cn.prosayj.authentication.csrf.util.json.JsonUtils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * JWT 工具类
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-14 下午 06:23
 * @since 1.0.0
 */
public class JWTUtils {

    public static final String TOKEN_HEADER = "Authorization";

    public static final String TOKEN_PREFIX = "Bearer ";

    private static final String SECRET = "5ae95dd3c5f811b9b819434910c52820ae7cfb3d9f7961e7117b24a7012873767d79f61f81fc2e06ebb6fd4f09ab47764d6e20607f843c22a0a2a6a6ed829680";

    /**
     * 签发人
     */
    private static final String ISSUER = "ProSayJ";

    /**
     * 默认过期时间秒
     */
    private static final long EXPIRATION = 60L;

    /**
     * 选择了记住我之后的过期时间 3600s * 7
     */
    private static final long EXPIRATION_REMEMBER = 3600L * 7;

    private JWTUtils() {
    }

    /**
     * 创建 Json Web Token
     *
     * @param username    {String} 用户名
     * @param rememberMe  {boolean} 是否记住我
     * @param userDetails {@link CustomUserDetails} 的实现类
     * @return String
     */
    public static String create(String username, boolean rememberMe, CustomUserDetails userDetails) {
        Map<String, Object> map = JsonUtils.toMap(userDetails);
        return Jwts.builder()
                // [Attention] 要先 setClaims(初始化底层 map) 再设置 subject, 如果 subject 先设置, 会被覆盖.
                .setClaims(map)
                // 主题
                .setSubject(username)
                // TODO 过期时间交由 Redis 处理
                .setExpiration(
                        Date.from(
                                LocalDateTime.now()
                                        .plus((rememberMe ? EXPIRATION_REMEMBER : EXPIRATION) * 1000, ChronoUnit.MILLIS)
                                        .atZone(ZoneId.systemDefault())
                                        .toInstant()
                        )
                )
                // 颁发时间
                .setIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                // 颁发人
                .setIssuer(ISSUER)
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SignatureAlgorithm.HS512)
                .serializeToJsonWith(JsonUtils::toJsonByte)
                .compact();
    }

    /**
     * 判断 Json Web Token 是否已经过期: true :已过期
     *
     * @param jwt jwt Json Web Token
     * @return boolean
     */
    public static boolean hasExpired(String jwt) {
        return Objects.requireNonNull(claims(jwt)).getExpiration().before(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
    }

    /**
     * 获得 subject
     *
     * @param jwt {String} Json Web Token
     * @return java.lang.String subject
     */
    public static String subject(String jwt) {
        return claims(jwt).getSubject();
    }

    public static CustomUserDetails userDetails(String jwt) {
        return JsonUtils.toBean(JsonUtils.toJsonString(claims(jwt)), CustomUserDetails.class);
    }

    /**
     * 获取jwt 中存储的主要信息
     *
     * @param jwt jwt
     * @return Claims
     */
    private static Claims claims(String jwt) {
        JwtParser build = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .deserializeJsonWith(bytes -> {
                    Map<String, Object> stringObjectMap = JsonUtils.toMap(bytes);
                    return stringObjectMap;
                })
                .build();
        return build.parseClaimsJws(jwt).getBody();
    }
}
