package cn.prosayj.springsecuritydemo.fliter;

import cn.prosayj.springsecuritydemo.fliter.expection.VerificationCodeExpection;
import cn.prosayj.springsecuritydemo.fliter.handler.MyAuthenticationFailuerHandler;
import org.springframework.security.access.method.P;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义验证码校验过滤器：
 * <p>
 * 虽然Spring Security的过滤器链对过滤器没有特殊要求， 只要继承了 Filter 即可，
 * 但是在 Spring 体系中， 推荐使用 {@link OncePerRequestFilter} 来实现. 它可以确保一次请求只会通过一次该过滤器（ Filter实际上并不能保证这一点） 。
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-10 上午 11:20
 * @since 1.0.0
 */
public class VerificationCodeFilter extends OncePerRequestFilter {
    private AuthenticationFailureHandler authenticationFailureHandler = new MyAuthenticationFailuerHandler();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        if (!"/auth/form".equals(request.getRequestURI())) {
            filterChain.doFilter(request, response);
        } else {
            try {
                verificationCode(request);
            } catch (VerificationCodeExpection verificationCodeExpection) {
                verificationCodeExpection.printStackTrace();
                authenticationFailureHandler.onAuthenticationFailure(request, response, verificationCodeExpection);
            }
        }


    }

    private void verificationCode(HttpServletRequest request) throws VerificationCodeExpection {
        String captcha = request.getParameter("captcha");

        String saveCode = (String) request.getSession().getAttribute("captcha");
        if (!StringUtils.isEmpty(saveCode)) {
            request.getSession().removeAttribute("captcha");
        }

        if (StringUtils.isEmpty(saveCode) || StringUtils.isEmpty(captcha) || !captcha.equals(saveCode)) {
            throw new VerificationCodeExpection();
        }


    }
}
