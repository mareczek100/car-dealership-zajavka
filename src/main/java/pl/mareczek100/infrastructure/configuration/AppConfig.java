package pl.mareczek100.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import pl.mareczek100.CarDealership;

@Configuration
@Import(DatabaseJpaConfig.class)
@ComponentScan(basePackageClasses = CarDealership.class)
@EnableWebMvc
public class AppConfig {


}