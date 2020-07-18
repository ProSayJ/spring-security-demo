package cn.prosayj.authentication.csrf.security.provider;

import cn.prosayj.authentication.csrf.constant.BaseConstant;
import cn.prosayj.authentication.csrf.model.user.domain.UserDomain;
import cn.prosayj.authentication.csrf.util.redis.RedisUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfAuthenticationStrategy;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.UUID;

/**
 * 基于 Redis 的 CSRF Token Repository
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-17 上午 11:43
 * @since 1.0.0
 */
@Slf4j
@Component
public class CsrfTokenRedisRepository implements CsrfTokenRepository {

    @Autowired
    private RedisUtil redisUtil;

    private UserDomain userDomain;

    private UserDomain setUserDomain(HttpServletRequest request) throws java.io.IOException {
//        this.userDomain = GateParamsFromRequest.getUserDomian(request);
//        request.getParameter("csrf-token");
//        return userDomain;
        UserDomain userDomain = new UserDomain("zs", "zs");
        this.userDomain = userDomain;
        return userDomain;
    }

    /**
     * 该方法会被调用次数: <br>
     * 1. 第一次是在 CsrfFilter 中, 当 loadToken 的调用返回 null 时;<br>
     * 2. (非匿名用户) 第二次是在
     * {@link CsrfAuthenticationStrategy#onAuthentication(Authentication, HttpServletRequest, HttpServletResponse)}
     * 用于执行清除, 此时传入的参数 token 为 null;
     * 3. (非匿名用户) 第三次实在
     * {@link CsrfAuthenticationStrategy#onAuthentication(Authentication, HttpServletRequest, HttpServletResponse)}
     * 用于更新;
     */
    @SneakyThrows
    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        log.debug("csrf filter: redis csrf token repository: save token");
        // request.getSession().setAttribute(CSRF_TOKEN_ATTR_NAME, token);

        if (Objects.isNull(token)) {
            // 由于过期时间交由 Redis 管理, 所以 token 为 null 时, 不进行任何操作直接返回.
            log.debug("csrf filter: do nothing while token is null. The token's lifecycle will be handled by Redis.");
            return;
        }

        redisUtil.put("user:" + userDomain.getName(), token.getToken(), 30);
        response.setHeader("csrf-token", token.getToken());
    }

    @SneakyThrows
    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        // HttpSession httpSession = Objects.requireNonNull(request.getSession(false), "当前无 Session!");
        // return (CsrfToken) httpSession.getAttribute(CSRF_TOKEN_ATTR_NAME);

        final UserDomain userDomain = setUserDomain(request);
        log.debug("csrf filter: redis csrf token repository: load token by UserDomain info ({}).", userDomain.toString());

        try {
            final String csrfToken = getCachedToken();
            return new DefaultCsrfToken(BaseConstant.CSRF_HEADER_NAME, BaseConstant.CSRF_PARAMETER_NAME, csrfToken);
        } catch (RuntimeException ignored) {
            return null;
        }
    }

    private String getCachedToken() {
        final String csrfToken = (String) redisUtil.get("user:" + userDomain.getName());
        if (csrfToken != null) {
            return csrfToken;
        }
        return BaseConstant.EMPTY;
    }


    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        final String csrfToken = StringUtils.replace(UUID.randomUUID().toString(), "-", BaseConstant.EMPTY);
        log.debug("csrf filter: redis csrf token repository: generate token: {}", csrfToken);

        return new DefaultCsrfToken(BaseConstant.CSRF_HEADER_NAME, BaseConstant.CSRF_PARAMETER_NAME, csrfToken);
    }

}