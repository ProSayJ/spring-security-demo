/*
 * Copyright (c) 2020. 布比（北京）网络技术有限公司.
 * All rights reserved.
 */

package cn.prosayj.authentication.jwt.security.filter;

import cn.prosayj.authentication.jwt.model.user.CustomUserDetails;
import cn.prosayj.authentication.jwt.model.response.ResponseWriter;
import cn.prosayj.authentication.jwt.model.response.RestBody;
import cn.prosayj.authentication.jwt.util.jwt.JWTUtils;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 鉴权过滤器<br>
 * 会通过这个过滤器的端点有 http://localhost:18902/task/**, http://localhost:18902/auth/register
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-14 下午 06:16
 * @since 1.0.0
 */
@Slf4j
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private static final Set<String> WHITE_LIST = Stream.of("/auth/register").collect(Collectors.toSet());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("authorization filter doFilterInternal");
        final String authorization = request.getHeader(JWTUtils.TOKEN_HEADER);
        log.debug("raw-access-token: {}", authorization);

        // Branch A: 如果请求头中没有 key  Authorization
        if (StringUtils.isEmpty(authorization)) {
            // 白名单放行
            if (WHITE_LIST.contains(request.getRequestURI())) {
                chain.doFilter(request, response);
            } else {
                ResponseWriter
                        .responseAccessDeniedJsonWriter(
                                response,
                                RestBody.failure(HttpServletResponse.SC_UNAUTHORIZED, "未经授权的访问! "));

            }
            return;
        }

        // Branch B: 如果请求头中有 Bear xxx, 设置认证信息
        final String jsonWebToken = authorization.replace(JWTUtils.TOKEN_PREFIX, "");

        // TODO 用 Redis 的过期控制 token, 而不用 jwt 的 Expiration
        try {
            JWTUtils.hasExpired(jsonWebToken);
        } catch (ExpiredJwtException e) {
            log.error("access-token 已过期", e);
            ResponseWriter
                    .responseAccessDeniedJsonWriter(
                            response,
                            RestBody.failure(HttpServletResponse.SC_UNAUTHORIZED, "access-token 已过期, 请重新登陆! " + e.getMessage()));
        }
        // TODO 每一次携带正确 token 的访问, 都刷新 Redis 的过期时间
        CustomUserDetails customUserDetails = JWTUtils.userDetails(jsonWebToken);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        customUserDetails.getName(),
                        // TODO Json Web Token 中不能携带用户密码
                        customUserDetails.getPassword(),
                        customUserDetails.getAuthorities()
                )
        );
        chain.doFilter(request, response);
    }
}

