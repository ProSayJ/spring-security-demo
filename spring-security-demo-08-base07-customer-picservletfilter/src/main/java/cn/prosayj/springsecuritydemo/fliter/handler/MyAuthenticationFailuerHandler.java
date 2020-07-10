package cn.prosayj.springsecuritydemo.fliter.handler;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义认证失败异常处理类
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-10 上午 11:32
 * @since 1.0.0
 */
public class MyAuthenticationFailuerHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter out = response.getWriter();
        out.write("{\"error_code\":\"401\",\"message\":\"" + exception.getMessage() + "\"}");
    }
}
