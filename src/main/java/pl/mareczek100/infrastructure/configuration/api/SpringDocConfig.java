package pl.mareczek100.infrastructure.configuration.api;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mareczek100.CarDealership;

@Configuration
public class SpringDocConfig {

    @Bean
    public GroupedOpenApi groupedOpenApi(){
        return GroupedOpenApi.builder()
                .group("default")
                .pathsToMatch("/**")
                .packagesToScan(CarDealership.class.getPackageName())
                .build();
    }

    @Bean
    public OpenAPI springDocOpenAPI(){
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Car Dealership App")
                        .contact(new Contact()
                                .name("Marek Jankowski")
                                .email("mareczek100@wp.pl"))
                        .version("1.0"));
    }

}
