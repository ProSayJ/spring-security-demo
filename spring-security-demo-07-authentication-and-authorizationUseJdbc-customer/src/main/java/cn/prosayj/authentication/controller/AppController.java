package cn.prosayj.authentication.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户端公开访问的API
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-09 下午 07:03
 * @since 1.0.0
 */
@RestController
@RequestMapping("/app/api")
public class AppController {


    @RequestMapping("/hello")
    public String hello() {
        return "hello app";
    }
}
