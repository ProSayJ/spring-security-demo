//package cn.prosayj.springsecuritydemo.authentication.provider.temp;
//
//import org.springframework.security.authentication.AuthenticationDetailsSource;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * MyAuthenticationProvider
// * <p>
// * DaoAuthenticationProvider的用户信息来源于UserDetailsService， 并且整合了密码编码的实现，
// * 在demo07的表单认证就是由DaoAuthenticationProvider提供的。
// *
// * @author yangjian@bubi.cn
// * @date 2020-07-10 下午 01:35
// * @since 1.0.0
// */
////@Component
//public class MyAuthenticationProvider_02 extends DaoAuthenticationProvider {
//
//
//    //构造注入
//    public MyAuthenticationProvider_02(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
//        this.setUserDetailsService(userDetailsService);
//        this.setPasswordEncoder(passwordEncoder);
//    }
//
//    /**
//     *
//     * @param userDetails    userDetails
//     * @param authentication authentication
//     * @throws AuthenticationException AuthenticationException
//     */
//    @Override
//    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
//        // 实现图形验证码的校验逻辑
//
//        // 调用父类方法完成密码的验证
//        super.additionalAuthenticationChecks(userDetails, authentication);
//    }
//}
