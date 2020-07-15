package cn.prosayj.authentication.configuration;

import cn.prosayj.authentication.fliter.VerificationCodeFilter;
import cn.prosayj.authentication.fliter.handler.MyAuthenticationFailuerHandler;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * WebSecurity配置
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-09 下午 05:34
 * @since 1.0.0
 */
@EnableWebSecurity(debug = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    /**
     * 为什么在数据库中的角色总是要添加“ROLE_”前缀， 在配置时却并没有“ROLE_”前缀呢？查看源码：
     * {@link ExpressionUrlAuthorizationConfigurer#hasRole(String)}
     * 如果不希望匹配这个前缀， 那么改为调用hasAuthority方法即可。
     *
     * @param http http
     * @throws Exception Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/api/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/user/api/**").hasRole("USER")
                .antMatchers("/app/api/**", "/captcha.jpg").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin()
                .loginPage("/myLogin.html")
                .loginProcessingUrl("/auth/form").permitAll()
                .failureHandler(new MyAuthenticationFailuerHandler());

        //将过滤器添加在 UsernamePasswordAuthenticationFilter 之前
        //有个问题，加了自定义的filter以后 验证码认证成功以后没有验证走验证pwd的逻辑
        http.addFilterBefore(new VerificationCodeFilter(), UsernamePasswordAuthenticationFilter.class);
    }


    /**
     * Bean 注解可以被SpringSecurity 发现并使用。
     * <p>
     * Spring Security支持各种来源的用户 数据， 包括内存、 数据库、 LDAP等。
     * 它们被抽象为一个UserDetailsService接口， 任何实现了 UserDetailsService 接口的对象都可以作为认证数据源。
     * 在这种设计模式下， Spring Security显得尤为灵活。
     * <p>
     * 仅仅调用createUser（ ） 生成两个用户， 并赋予相应的角色。 它会工作得很好， 多次重启服务
     * 也不会出现问题。 为什么要强调多次重启服务呢？ 下一个demo
     * <p>
     * JdbcUserDetailsManager与InMemoryUserDetailsManager在用法上没有太大区别， 只是多了设置
     * DataSource 的环节。 Spring Security 通过 DataSource 执行设定好的命令。 例如， 此处的createUser函数
     * 实际上就是执行了下面的SQL语句。insert into users (username, password, enabled) values (?,?,?)
     * <p>
     * 查看 JdbcUserDetailsManager 的源代码可以看到更多定义好的 SQL 语句。
     */
//    @Override
//    @Bean
//    public UserDetailsService userDetailsServiceBean() throws Exception {
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
//        jdbcUserDetailsManager.setDataSource(dataSource);
//
//        if (!jdbcUserDetailsManager.userExists("user")) {
//            jdbcUserDetailsManager.createUser(
//                    User
//                            .withUsername("user")
//                            .password("123")
//                            .roles("USER")
//                            .build());
//        }
//        if (!jdbcUserDetailsManager.userExists("admin")) {
//            jdbcUserDetailsManager.createUser(
//                    User
//                            .withUsername("admin")
//                            .password("123")
//                            .roles("USER", "ADMIN")
//                            .build());
//        }
//        return jdbcUserDetailsManager;
//    }

    /**
     * Spring Security支持各种来源的用户，定义数据源的另外一种写法.两种写法只需要一种即可
     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication().dataSource(dataSource)
//                .withUser("user").password("123").roles("user")
//                .and()
//                .withUser("admin").password("123").roles("admin");
//    }
}
