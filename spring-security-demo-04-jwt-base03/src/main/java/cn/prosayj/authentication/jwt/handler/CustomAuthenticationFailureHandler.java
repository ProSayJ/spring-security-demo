/*
 * Copyright (c) 2020. 布比（北京）网络技术有限公司.
 * All rights reserved.
 */

package cn.prosayj.authentication.jwt.handler;

import cn.prosayj.authentication.jwt.model.response.ResponseWriter;
import cn.prosayj.authentication.jwt.model.response.RestBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * CustomAuthenticationFailureHandler
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-15 下午 11:03
 * @since 1.0.0
 */
@Slf4j
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) {
        log.debug("::: 认证失败");
        ResponseWriter
                .responseAccessDeniedJsonWriter(
                        httpServletResponse,
                        RestBody.failure(HttpServletResponse.SC_UNAUTHORIZED, "认证失败 " + e.getMessage()));
    }

}