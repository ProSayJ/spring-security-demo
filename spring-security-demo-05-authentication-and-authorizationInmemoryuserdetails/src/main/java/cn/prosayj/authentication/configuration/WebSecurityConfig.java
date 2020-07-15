package cn.prosayj.authentication.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * WebSecurity配置
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-09 下午 05:34
 * @since 1.0.0
 */
@EnableWebSecurity(debug = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/admin/api/**").hasRole("ADMIN")
//                .antMatchers("/user/api/**").hasRole("USER")
//                .antMatchers("/app/api/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin();
//
//    }

    @Override
    @Bean
    /**
     * Bean 注解可以被SpringSecurity 发现并使用。
     *
     * Spring Security支持各种来源的用户 数据， 包括内存、 数据库、 LDAP等。
     * 它们被抽象为一个UserDetailsService接口， 任何实现了 UserDetailsService 接口的对象都可以作为认证数据源。
     * 在这种设计模式下， Spring Security显得尤为灵活。
     *
     * 仅仅调用createUser（ ） 生成两个用户， 并赋予相应的角色。 它会工作得很好， 多次重启服务
     * 也不会出现问题。 为什么要强调多次重启服务呢？ 下一个demo
     */
    public UserDetailsService userDetailsServiceBean() throws Exception {
        InMemoryUserDetailsManager memoryUserDetailsManager = new InMemoryUserDetailsManager();
        memoryUserDetailsManager.createUser(
                User
                        .withUsername("user")
                        .password("123")
                        .roles("USER")
                        .build());
        memoryUserDetailsManager.createUser(
                User
                        .withUsername("admin")
                        .password("123")
                        .roles("USER", "ADMIN")
                        .build());
        return memoryUserDetailsManager;
    }

    /**
     * Spring Security支持各种来源的用户，定义数据源的另外一种写法.两种写法只需要一种即可
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("123").roles("user")
                .and()
                .withUser("admin").password("123").roles("admin");
    }
}
