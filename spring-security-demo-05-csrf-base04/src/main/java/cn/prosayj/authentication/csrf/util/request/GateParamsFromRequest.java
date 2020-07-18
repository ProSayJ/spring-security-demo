package cn.prosayj.authentication.csrf.util.request;

import cn.prosayj.authentication.csrf.model.user.domain.UserDomain;
import cn.prosayj.authentication.csrf.util.json.JsonUtils;
import io.jsonwebtoken.lang.Collections;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

/**
 * TODO
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-17 下午 03:05
 * @since 1.0.0
 */
public class GateParamsFromRequest {

    public static UserDomain getUserDomian(HttpServletRequest request) throws IOException {
        UserDomain userDomain;
        // 如果登录提交方式是 form表单提交，则数据由request.getParameterMap()中获取。
        // 如果登录提交方式是 json 提交，则数据由 requestBody 传输
        if (!Collections.isEmpty(request.getParameterMap())) {
            userDomain = new UserDomain(request.getParameter("name"), request.getParameter("password"));
        } else {
            userDomain = JsonUtils.toBean(request.getInputStream(), UserDomain.class);
        }
        Optional.ofNullable(userDomain).orElseThrow(() -> new AuthenticationException("LoginUser info is null!") {
        });
        return userDomain;
    }
}
