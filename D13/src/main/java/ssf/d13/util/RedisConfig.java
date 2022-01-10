package ssf.d13.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import ssf.d13.model.Contact;


@Configuration
public class RedisConfig {

    // Creating Connection with Redis
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    // Creating RedisTemplate for Entity 'Employee'
    @Bean
    public RedisTemplate<String, Contact> redisTemplate() {
        RedisTemplate<String, Contact> contactTemplate = new RedisTemplate<>();
        contactTemplate.setConnectionFactory(redisConnectionFactory());
        return contactTemplate;
    }
}
