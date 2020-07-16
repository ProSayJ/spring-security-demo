/*
 * Copyright (c) 2020. 布比（北京）网络技术有限公司.
 * All rights reserved.
 */

package cn.prosayj.authentication.jwt.provider;

import cn.prosayj.authentication.jwt.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.method.P;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 自定义的认证器 AuthenticationProvider
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-14 下午 05:47
 * @since 1.0.0
 */
@Slf4j
@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (StringUtils.isEmpty(authentication.getName()) || authentication.getCredentials() == null) {
            throw new AuthenticationException("用户名或密码错误") {
            };
        }
        // 获取用户输入的用户名和密码
        final String username = authentication.getName();
        final String password = authentication.getCredentials().toString();

        log.info("authenticate start loadUserByUsername::{}", username);

        // 获取封装用户信息的对象
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // 进行密码的比对
        boolean flag = passwordEncoder.matches(password, userDetails.getPassword());
        // 校验通过
        if (!flag) {
            throw new AuthenticationException("用户名或密码错误") {
            };
        }
        // 封装权限信息
        log.info("authenticate end loadUserByUsername::{}", JsonUtils.toJsonString(userDetails));
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
