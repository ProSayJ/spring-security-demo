package cn.prosayj.authentication;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@MapperScan("cn.prosayj.springsecuritydemo.modules")
public class SecurityAuthenticationAndAuthorizationAuthentication_08 {

    public static void main(String[] args) {
        SpringApplication.run(SecurityAuthenticationAndAuthorizationAuthentication_08.class, args);
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello spring security";
    }

}
