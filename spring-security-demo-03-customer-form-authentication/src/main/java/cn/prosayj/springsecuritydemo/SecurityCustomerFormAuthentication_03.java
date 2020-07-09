package cn.prosayj.springsecuritydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SecurityCustomerFormAuthentication_03 {

    public static void main(String[] args) {
        SpringApplication.run(SecurityCustomerFormAuthentication_03.class, args);
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello spring security";
    }

}
