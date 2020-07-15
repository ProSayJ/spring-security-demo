package cn.prosayj.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SecurityAuthenticationAndAuthorizationAuthentication_05 {

    public static void main(String[] args) {
        SpringApplication.run(SecurityAuthenticationAndAuthorizationAuthentication_05.class, args);
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello spring security";
    }

}
