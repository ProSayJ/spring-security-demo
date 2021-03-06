package cn.prosayj.authentication.csrf.constant;

/**
 * 常量类
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-16 下午 11:22
 * @since 1.0.0
 */
public class BaseConstant {
    /**
     * 默认时区
     */
    public static final String DEFAULT_TIMEZONE_ID = "GMT+8";

    /**
     * access-token
     */
    public static final String ACCESS_TOKE = "access-token";

    /**
     * 默认字符集编码
     */
    public static final String DEFAULT_CHARACTER_SET = "utf-8";


    /**
     * 空字符串
     */
    public static final String EMPTY = "";

    /**
     * parameterName
     */
    public static final String CSRF_PARAMETER_NAME = "_csrf";

    /**
     * headerName
     */
    public static final String CSRF_HEADER_NAME = "X-CSRF-TOKEN";
}
