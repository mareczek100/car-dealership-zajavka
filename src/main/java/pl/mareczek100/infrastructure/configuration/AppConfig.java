package pl.mareczek100.infrastructure.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import pl.mareczek100.CarDealership;

@Configuration
@Import({DatabaseJpaConfig.class, ViewAndThymeleafConfig.class, WebAppInitializer.class})
@ComponentScan(basePackageClasses = CarDealership.class)
public class AppConfig {


}