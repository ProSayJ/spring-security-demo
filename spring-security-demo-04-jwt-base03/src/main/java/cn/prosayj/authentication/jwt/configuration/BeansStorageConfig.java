package cn.prosayj.authentication.jwt.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * BeanStorage
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-15 下午 11:18
 * @since 1.0.0
 */
@Configuration
public class BeansStorageConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
