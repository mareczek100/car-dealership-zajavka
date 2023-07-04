package pl.mareczek100.integration;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import pl.mareczek100.config.AbstractTestConfigIT;


@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MainPageControllerTestIT extends AbstractTestConfigIT {
    private final static String HOME_WELCOME = "This is Car Dealership Site!";

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