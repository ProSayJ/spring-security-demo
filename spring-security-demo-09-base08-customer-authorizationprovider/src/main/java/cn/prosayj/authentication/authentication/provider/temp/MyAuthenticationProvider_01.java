//package cn.prosayj.springsecuritydemo.authentication.provider.temp;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Component;
//
///**
// * 自定义认证过程
// * <p>
// * Spring Security提供了多种常见的认证技术， 包括但不限于以下几种：
// * 1.HTTP层面的认证技术， 包括HTTP基本认证和HTTP摘要认证两种。
// * 2.基于LDAP的认证技术（ Lightweight Directory Access Protocol， 轻量目录访问协议） 。
// * 3.聚焦于证明用户身份的OpenID认证技术。
// * 4.聚焦于授权的OAuth认证技术。
// * 5.系统内维护的用户名和密码认证技术。
// * <p>
// * 其中， 使用最为广泛的是由系统维护的用户名和密码认证技术， 通常会涉及数据库访问。 为了更
// * 好地按需定制， Spring Security 并没有直接糅合整个认证过程， 而是提供了一个抽象的AuthenticationProvider。
// * <p>
// * 在 AbstractUserDetailsAuthenticationProvider 中实现了基本的认证流程， 通过继承 AbstractUserDetailsAuthenticationProvider，
// * 并实现retrieveUser和additionalAuthenticationChecks两个抽象方法即可自定义核心认证过程， 灵活性非常高。
// * <p>
// * <p>
// * Spring Security 同样提供一个继承自 AbstractUserDetailsAuthenticationProvider 的 AuthenticationProvider。
// *
// * @author yangjian@bubi.cn
// * @date 2020-07-10 下午 01:24
// * @since 1.0.0
// */
////@Component
//public class MyAuthenticationProvider_01 extends AbstractUserDetailsAuthenticationProvider {
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//
//    @Override
//    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
//        //TODO 其他校验逻辑
//
//        if (authentication.getCredentials() == null) {
//            throw new BadCredentialsException(
//                    this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials",
//                            "密码不能为空"));
//        } else {
//            String pwd = authentication.getCredentials().toString();
//            if (!pwd.equals(userDetails.getPassword())) {
//                throw new BadCredentialsException(
//                        this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials",
//                                "密码错误"));
//            }
//
//        }
//
//    }
//
//    @Override
//    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
//        return userDetailsService.loadUserByUsername(username);
//    }
//}
