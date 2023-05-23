package pl.mareczek100.infrastructure.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.mareczek100.CarDealership;

@Configuration
@Import(DatabaseJpaConfig.class)
@ComponentScan(basePackageClasses = CarDealership.class)
public class AppBeansConfig {


}