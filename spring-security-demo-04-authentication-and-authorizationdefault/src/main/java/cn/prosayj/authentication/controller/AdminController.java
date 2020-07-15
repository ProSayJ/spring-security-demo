package cn.prosayj.authentication.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 统后台管理相关的 API
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-09 下午 07:03
 * @since 1.0.0
 */
@RestController
@RequestMapping("/admin/api")
public class AdminController {


    /**
     * 页面显示403错误， 表示该用户授权失败（ 401代表该用户认证失败） 。
     * 也就是说， 本次访问已经通过了认证环节， 只是在授权的时候被驳回了。
     * 认证环节是没有问题的， 因为Spring Security默认的用 户角色正是user。
     * <p>
     * HTTP状态码（ HTTP Status Code） 是由RFC 2616定义的一种用来表示一个HTTP请求响应状态的
     * 规范， 由3位数字组成。 通常用2XX表示本次操作成功， 用4XX表示是客户端导致的失败， 用5XX表示
     * 是服务器引起的错误。
     */
    @RequestMapping("/hello")
    public String hello() {
        return "hello admin";
    }
}
