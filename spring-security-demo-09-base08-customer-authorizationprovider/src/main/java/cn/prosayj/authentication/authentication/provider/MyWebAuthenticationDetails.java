package cn.prosayj.authentication.authentication.provider;

import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 该类提供给一个自定义的AuthenticationDetailsSource {@link MyWebAuthenticationDetailsSource}。
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-10 下午 02:25
 * @since 1.0.0
 */
public class MyWebAuthenticationDetails extends WebAuthenticationDetails {
    private boolean imageCodeIsRight;
    private String imageCode;
    private String saveImageCode;

    public boolean isImageCodeIsRight() {
        return this.imageCodeIsRight;
    }

    /**
     * Records the remote address and will also set the session Id if a session already
     * exists (it won't create one).
     *
     * @param request that the authentication request was received from
     */
    public MyWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        this.imageCode = request.getParameter("captcha");
        this.saveImageCode = (String) request.getSession().getAttribute("captcha");
        if (!StringUtils.isEmpty(this.saveImageCode)) {
            request.getSession().removeAttribute("captcha");
        }
        if (!StringUtils.isEmpty(imageCode) && imageCode.equals(saveImageCode)) {
            imageCodeIsRight = true;
        }
    }
}
