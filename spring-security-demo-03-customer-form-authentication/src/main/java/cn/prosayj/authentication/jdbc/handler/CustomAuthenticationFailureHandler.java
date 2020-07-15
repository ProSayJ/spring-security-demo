/*
 * Copyright (c) 2020. 布比（北京）网络技术有限公司.
 * All rights reserved.
 */

package cn.prosayj.authentication.jdbc.handler;

import cn.prosayj.authentication.jdbc.model.ResponseWriter;
import cn.prosayj.authentication.jdbc.model.RestBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        log.debug("::: 认证失败");
        ResponseWriter
                .responseAccessDeniedJsonWriter(
                        httpServletResponse,
                        RestBody.failure(HttpServletResponse.SC_UNAUTHORIZED, "认证失败 " + e.getMessage()));
    }

}