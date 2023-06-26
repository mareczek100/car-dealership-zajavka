package pl.mareczek100.infrastructure.config;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import pl.mareczek100.CarDealership;

@ActiveProfiles("test")
@Import({PersistenceTestConfig.class, TestRestTemplateSecurityConfig.class})
@SpringBootTest(
        classes = CarDealership.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public abstract class AbstractTestConfigIT {



}