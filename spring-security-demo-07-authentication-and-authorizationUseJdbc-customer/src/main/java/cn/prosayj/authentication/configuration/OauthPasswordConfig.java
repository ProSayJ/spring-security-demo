package cn.prosayj.authentication.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * TODO
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-10 下午 11:09
 * @since 1.0.0
 */
@Configuration
public class OauthPasswordConfig {

    /**
     * 必须注入，验证密码需要使用
     *
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("123"));
    }
}
