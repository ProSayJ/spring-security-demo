package cn.prosayj.authentication.configuration;

import cn.prosayj.authentication.auth.Oauth2AccessDecisionManager;
import cn.prosayj.authentication.auth.Oauth2FilterInvocationSecurityMetadataSource;
import cn.prosayj.authentication.auth.Oauth2FilterSecurityInterceptor;
import cn.prosayj.authentication.service.impl.CustomerUserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/**
 * WebSecurity配置
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-09 下午 05:34
 * @since 1.0.0
 */
@Order(2)
@EnableWebSecurity(debug = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomerUserDetailServiceImpl userDetailService;
    @Autowired
    AuthenticationManager manager;

    @Autowired
    Oauth2AccessDecisionManager accessDecisionManager;

    @Autowired
    Oauth2FilterInvocationSecurityMetadataSource securityMetadataSource;

    /**
     * 配置用户登录验证服务
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().disable();
        http.httpBasic().disable();
        http.authorizeRequests().anyRequest().authenticated();
    }
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/admin/api/**").hasRole("ADMIN")
//                .antMatchers("/user/api/**").hasRole("USER")
//                .antMatchers("/app/api/**").permitAll()
//                .anyRequest().authenticated()
////                .and()
////                .formLogin()
////                .failureHandler(new AuthenticationFailureHandler() {
////                    @Override
////                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
////                        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
////                        System.out.println("==================401========================");
////                        PrintWriter out = response.getWriter();
////                        out.write("{\"error_code\":\"401\",\"message\":\"" + "身份验证错误====>" + exception.getMessage() + "\"}");
////                    }
////                })
//                .and()
//                .csrf().disable();
//        http.authorizeRequests().anyRequest().authenticated();
//        http.addFilterAfter(createApiAuthenticationFilter(), FilterSecurityInterceptor.class);
//
//    }

    /**
     * API权限控制
     * 过滤器优先度在 FilterSecurityInterceptor 之后
     * spring-security 的默认过滤器列表见 https://docs.spring.io/spring-security/site/docs/5.0.0.M1/reference/htmlsingle/#ns-custom-filters
     *
     * @return
     */
    private Oauth2FilterSecurityInterceptor createApiAuthenticationFilter() {
        Oauth2FilterSecurityInterceptor interceptor = new Oauth2FilterSecurityInterceptor();
        interceptor.setAuthenticationManager(manager);
        interceptor.setAccessDecisionManager(accessDecisionManager);
        interceptor.setSecurityMetadataSource(securityMetadataSource);
        return interceptor;
    }

}
