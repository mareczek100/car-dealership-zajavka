package pl.mareczek100.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
@TestConfiguration
public class RestTemplateSecurityTestConfig {
//    @Value("${spring.security.user.name}")
//    private String userName;
//
//    @Value("${spring.security.user.password}")
//    private String password;

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder().basicAuthentication("userName", "password");
    }
}
