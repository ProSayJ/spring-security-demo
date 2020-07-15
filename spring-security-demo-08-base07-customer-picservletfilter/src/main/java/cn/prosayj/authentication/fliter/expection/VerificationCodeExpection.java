package cn.prosayj.authentication.fliter.expection;


import org.springframework.security.core.AuthenticationException;

/**
 * 自定义验证码校验过滤器异常
 * <p>
 * 虽然Spring Security的过滤器链对过滤器没有特殊要求， 只要继承了 Filter 即可， 但是在 Spring 体系中， 推荐使用
 * OncePerRequestFilter来实现， 它可以确保一次请求只会通过一次该过滤器（ Filter实际上并不能保证这一点） 。
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-10 上午 11:18
 * @since 1.0.0
 */
public class VerificationCodeExpection extends AuthenticationException {
    public VerificationCodeExpection() {
        super("图形验证码校验失败");
    }
}
