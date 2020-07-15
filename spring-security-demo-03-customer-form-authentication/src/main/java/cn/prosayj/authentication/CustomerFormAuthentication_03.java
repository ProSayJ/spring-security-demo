/*
 * Copyright (c) 2020. 布比（北京）网络技术有限公司.
 * All rights reserved.
 */

package cn.prosayj.authentication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CustomerFormAuthentication_03
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-14 下午 03:50
 * @since 1.0.0
 */
@Slf4j
@SpringBootApplication
@RestController
public class CustomerFormAuthentication_03 {

    public static void main(String[] args) {
        SpringApplication.run(CustomerFormAuthentication_03.class, args);
    }

    @GetMapping("/hello")
    public String hello() throws JsonProcessingException {
        log.info("authentication::{}", new ObjectMapper().writeValueAsString(SecurityContextHolder.getContext().getAuthentication()));
        return "hello spring security";
    }

}
