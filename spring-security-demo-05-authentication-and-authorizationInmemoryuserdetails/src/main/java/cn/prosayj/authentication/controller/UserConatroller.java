package cn.prosayj.authentication.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户操作自身数据相关的API
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-09 下午 07:03
 * @since 1.0.0
 */
@RestController
@RequestMapping("/user/api")
public class UserConatroller {

    @RequestMapping("/hello")
    public String hello() {
        return "hello user";
    }
}
