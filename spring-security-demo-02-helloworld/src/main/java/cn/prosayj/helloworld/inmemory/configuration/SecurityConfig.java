/*
 * Copyright (c) 2020. 布比（北京）网络技术有限公司.
 * All rights reserved.
 */

package cn.prosayj.helloworld.inmemory.configuration;

import cn.prosayj.helloworld.inmemory.handler.CustomAuthenticationFailureHandler;
import cn.prosayj.helloworld.inmemory.handler.CustomSimpleUrlAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring security config
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-09 下午 05:34
 * @since 1.0.0
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    public CustomSimpleUrlAuthenticationSuccessHandler customSimpleUrlAuthenticationSuccessHandler;
    public CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    public PasswordEncoder passwordEncoder;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setCustomAuthenticationFailureHandler(CustomAuthenticationFailureHandler customAuthenticationFailureHandler) {
        this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
    }

    @Autowired
    public void setCustomSimpleUrlAuthenticationSuccessHandler(CustomSimpleUrlAuthenticationSuccessHandler customSimpleUrlAuthenticationSuccessHandler) {
        this.customSimpleUrlAuthenticationSuccessHandler = customSimpleUrlAuthenticationSuccessHandler;
    }


    /**
     * 用户名/密码配置
     *
     * 注意；在SpringSecuriy 5.x版本 AuthenticationManagerBuilder 默认 密码为 BCryptPasswordEncoder
     * 而当前版本默认是 NoOpPasswordEncoder 。所以需要手动指定
     *
     * @param auth auth
     * @throws Exception Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Java 配置用户名 / 密码
        auth
                .inMemoryAuthentication()
                .withUser("admin").roles("ADMIN").password(passwordEncoder.encode("admin"))
                .and()
                .withUser("zs").roles("USER").password(passwordEncoder.encode("zs"))
                .and().passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 任何 URL 均由认证过的用户访问
        http.authorizeRequests().anyRequest().authenticated()
                .and()
                //启用了 httpBasic (用户名和密码会按以:拼接再 Base64 编码放到请求头的 Authorization 中,
                // 是最基础/不安全的认证方式) 和 表单登陆并自定义了表单登陆成功和失败的处理类。
                .httpBasic()
                .and()
                .formLogin()
                .failureHandler(customAuthenticationFailureHandler)
                .successHandler(customSimpleUrlAuthenticationSuccessHandler)
                // Cross Site Request Forgery: 跨站请求伪造; Reference: https://docs.spring.io/spring-security/site/docs/3.2.0.CI-SNAPSHOT/reference/html/csrf.html
                // Reference: demo-spring-security-csrf
                .and()
                //禁用了 csrf
                .csrf().disable()
        ;
    }


}
