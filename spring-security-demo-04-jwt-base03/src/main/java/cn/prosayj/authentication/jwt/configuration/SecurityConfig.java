/*
 * Copyright (c) 2020. 布比（北京）网络技术有限公司.
 * All rights reserved.
 */

package cn.prosayj.authentication.jwt.configuration;

import cn.prosayj.authentication.jwt.filter.JWTAuthenticationFilter;
import cn.prosayj.authentication.jwt.filter.JWTAuthorizationFilter;
import cn.prosayj.authentication.jwt.handler.CustomAuthenticationFailureHandler;
import cn.prosayj.authentication.jwt.handler.CustomSimpleUrlAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * WebSecurity配置
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-09 下午 05:34
 * @since 1.0.0
 */
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    @Qualifier("usernamePasswordAuthenticationProvider")
    private AuthenticationProvider usernamePasswordAuthenticationProvider;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        // 配置自定义的AuthenticationProvider校验器
        auth.authenticationProvider(usernamePasswordAuthenticationProvider);
    }

    /**
     * 查阅 WebSecurityConfigurerAdapter 源代码我们知道, HttpSecurity 默认是启用了 httpBasic 和 formLogin.
     *
     * @param http http
     * @throws Exception Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                // Cross Site Request Forgery: 跨站请求伪造; Reference: https://docs.spring.io/spring-security/site/docs/3.2.0.CI-SNAPSHOT/reference/html/csrf.html
                // Reference: demo-spring-security-csrf
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .anyRequest().hasAnyAuthority("ADMIN") // .authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                // 让校验 Token 的过滤器在身份认证过滤器之后
                .addFilterAfter(new JWTAuthorizationFilter(), JWTAuthenticationFilter.class)
                // 不需要 Session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //.and().formLogin().disable();

    }

}
