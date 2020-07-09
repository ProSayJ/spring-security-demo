package cn.prosayj.springsecuritydemo.configuration;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * WebSecurity配置
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-09 下午 05:34
 * @since 1.0.0
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
}
