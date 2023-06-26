package pl.mareczek100.integration;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import pl.mareczek100.infrastructure.config.AbstractTestConfigIT;


@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MainPageControllerTest extends AbstractTestConfigIT {

    private final static String HOME_ENDPOINT = "/car-dealership/";
    private final static String HOME_WELCOME = "This is Car Dealership Site!";

    @LocalServerPort
    private int port;

    private final TestRestTemplate testRestTemplate;

    @BeforeEach
    void setUp(){
        org.junit.jupiter.api.Assertions.assertNotNull(testRestTemplate);
    }

    @Test
    void homePageWorksFine(){
        String url = String.format("http://localhost:%s%s", port, HOME_ENDPOINT);

        String page = testRestTemplate.getForObject(url, String.class);
        Assertions.assertThat(page).contains(HOME_WELCOME);
    }
}
