package cn.prosayj.springsecuritydemo.configuration;

import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * WebSecurity配置
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-09 下午 05:34
 * @since 1.0.0
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/myLogin.html")
                //指定处理登录请求的路径
                .loginProcessingUrl("/authentication/form")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                        PrintWriter out = response.getWriter();
                        out.write("{\"error_code\":\"0\",\"message\":\"欢迎登录系统\"}");
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                        PrintWriter out = response.getWriter();
                        out.write("{\"error_code\":\"401\",\"message\":\"" + exception.getMessage() + "\"}");
                    }
                })
                //登录页面不设置访问限制
                .permitAll()
                .and()
                //csrf 是 spring security 提供的跨站请求伪造功能的防护。继承WebSecurityConfigurerAdapter时会默认开启
                .csrf().disable();

    }
}
