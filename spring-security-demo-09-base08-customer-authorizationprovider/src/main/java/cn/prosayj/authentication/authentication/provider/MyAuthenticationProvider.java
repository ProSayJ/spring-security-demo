package cn.prosayj.authentication.authentication.provider;

import cn.prosayj.authentication.fliter.expection.VerificationCodeExpection;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * MyAuthenticationProvider
 * <p>
 * 验证流程中添加新的逻辑后似乎有些问题。 在additionalAuthenticationChecks中， 我们可以得到
 * 的参数是来自UserDetailsService的UserDetails， 以及根据用户提交的账号信息封装而来的
 * UsernamePasswordAuthenticationToken， 而图形验证码的校验必须要有HttpServletRequest对象， 因为用
 * 户提交的验证码和session存储的验证码都需要从用户的请求中获取， 这是否意味着这种实现方式不可行呢？ 并非如此， Authentication实际上还可以携带账号信息之外的数据。
 * <p>
 * 在验证流程中添加新的逻辑后似乎有些问题。 在additionalAuthenticationChecks中， 我们可以得到
 * 的参数是来自UserDetailsService的UserDetails， 以及根据用户提交的账号信息封装而来的
 * UsernamePasswordAuthenticationToken， 而图形验证码的校验必须要有HttpServletRequest对象， 因为用
 * 户提交的验证码和session存储的验证码都需要从用户的请求中获取， 这是否意味着这种实现方式不可
 * 行呢？ 并非如此， Authentication 实际上还可以携带账号信息之外的数据。{@link Authentication#getDetails()}
 * <p>
 * 如果这个数据可以利用， 那么难题自然就迎刃而解了。
 * <p>
 * 一次完整的认证可以包含多个AuthenticationProvider， 这些AuthenticationProvider都是由ProviderManager管理的， 而
 * ProviderManager 是由 UsernamePasswordAuthenticationFilter 调用的。 也就是说， 所有的
 * AuthenticationProvider包含的Authentication都来源于:
 * {@link UsernamePasswordAuthenticationFilter#attemptAuthentication(HttpServletRequest, HttpServletResponse)}  }。
 * <p>
 * {@link AbstractAuthenticationProcessingFilter}本身并没有设置用户详细信息的流程， 而且是通过标准接口
 * {@link AuthenticationDetailsSource }构建的， 这意味着它是一个允许定制的特性。
 * <p>
 * 在 {@link UsernamePasswordAuthenticationFilter} 中使用的 AuthenticationDetailsSource 是一个标准的Web认证源， 携带的是用户的sessionId和IP地址。
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-10 下午 02:40
 * @since 1.0.0
 */
@Component
public class MyAuthenticationProvider extends DaoAuthenticationProvider {
//    @Autowired
//    private UserDetailsService userDetailsService;
    //构造注入
    public MyAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.setUserDetailsService(userDetailsService);
        this.setPasswordEncoder(passwordEncoder);
    }

    /**
     * 密码编码器
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        // 实现图形验证码的校验逻辑
        MyWebAuthenticationDetails details = (MyWebAuthenticationDetails) authentication.getDetails();
        if (!details.isImageCodeIsRight()) {
            throw new VerificationCodeExpection();
        }
        // 调用父类方法完成密码验证
        super.additionalAuthenticationChecks(userDetails, authentication);
    }
}
