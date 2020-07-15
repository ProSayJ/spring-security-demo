package cn.prosayj.authentication.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 统后台管理相关的 API，
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-09 下午 07:03
 * @since 1.0.0
 */
@RestController
@RequestMapping("/admin/api")
public class AdminController {

    @RequestMapping("/hello")
    public String hello(){
        return "hello admin";
    }
}
