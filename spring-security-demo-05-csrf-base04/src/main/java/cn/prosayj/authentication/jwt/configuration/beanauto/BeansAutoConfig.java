package cn.prosayj.authentication.jwt.configuration.beanauto;

import cn.prosayj.authentication.jwt.util.redis.RedisUtil;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.TimeZone;

import static cn.prosayj.authentication.jwt.constant.BaseConstant.DEFAULT_TIMEZONE_ID;

/**
 * BeansAutoConfiguration
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-15 下午 11:18
 * @since 1.0.0
 */
@Configuration
public class BeansAutoConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomization() {
        return jacksonObjectMapperBuilder ->
                jacksonObjectMapperBuilder.timeZone(TimeZone.getTimeZone(DEFAULT_TIMEZONE_ID));
    }

    /**
     * 注入封装RedisTemplate
     */
    @Bean(name = "redisUtil")
    public RedisUtil redisUtil(RedisTemplate<String, Object> redisTemplate) {
        return new RedisUtil(redisTemplate);
    }
}
