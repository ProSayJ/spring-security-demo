package cn.prosayj.helloworld;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@SpringBootApplication
@RestController
public class HelloWorld_01 {

    public static void main(String[] args) {
        SpringApplication.run(HelloWorld_01.class, args);
    }

    @GetMapping("/hello")
    public String hello() throws JsonProcessingException {

        log.info("authentication::{}", new ObjectMapper().writeValueAsString(SecurityContextHolder.getContext().getAuthentication()));
        return "hello spring security";
    }

}
