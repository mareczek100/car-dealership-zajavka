package pl.mareczek100.config;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import pl.mareczek100.CarDealership;

@ActiveProfiles("test")
@Import({PersistenceTestConfig.class, RestTemplateSecurityTestConfig.class})
@SpringBootTest(
        classes = CarDealership.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public abstract class AbstractTestConfigIT {
    protected final static String HOME_ENDPOINT = "/car-dealership/";
    @LocalServerPort
    protected int port;
}