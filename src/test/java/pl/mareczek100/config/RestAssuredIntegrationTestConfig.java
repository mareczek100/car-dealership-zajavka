package pl.mareczek100.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.util.Map;

public abstract class RestAssuredIntegrationTestConfig extends AbstractTestConfigIT implements ControllerTestSupport {

    protected static WireMockServer wireMockServer;
    private String JSESSIONID;
    private final static String LOGIN = "/login";
    private final static String LOGOUT = "/logout";
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll() {
        wireMockServer = new WireMockServer(
                WireMockConfiguration.wireMockConfig()
                        .port(9999)
                        .extensions(new ResponseTemplateTransformer(false))
        );
        wireMockServer.start();
    }

    @AfterAll
    static void afterAll() {
        wireMockServer.stop();
    }

    @Override
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @Test
    void contextLoaded() {
        Assertions.assertTrue(true, "Context loaded");
    }

    @AfterEach
    void afterEach() {
        logout().and().cookie("JSESSIONID", "");
        wireMockServer.resetAll();
        JSESSIONID = null;
    }

    @BeforeEach
    void beforeEach() {
        JSESSIONID = login("test", "test");
    }

    public RequestSpecification requestSpecification() {
        return restAssuredBase()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .cookie("JSESSIONID", JSESSIONID);
    }
    public RequestSpecification requestTestSpecification() {
        return restAssuredBase();

    }

    private RequestSpecification restAssuredBase() {
        return RestAssured
                .given()
                .config(getConfig())
                .basePath(HOME_ENDPOINT)
                .port(port);
    }

    private RestAssuredConfig getConfig() {
        return RestAssuredConfig.config()
                .objectMapperConfig(new ObjectMapperConfig()
                        .jackson2ObjectMapperFactory((type, s) -> objectMapper));
    }

    private String login(final String usernameTest, final String passwordTest) {
        return requestTestSpecification()
                .params(Map.of(
                        "username", usernameTest,
                        "password", passwordTest
                ))
                .post(LOGIN)
                .then()
                .statusCode(HttpStatus.FOUND.value())
                .and()
                .cookie("JSESSIONID")
                .header(HttpHeaders.LOCATION, "http://localhost:%s%s".formatted(port, HOME_ENDPOINT))
                .extract()
                .cookie("JSESSIONID");

    }
    private ValidatableResponse logout() {
        return requestSpecification()
                .post(LOGOUT)
                .then()
                .statusCode(HttpStatus.FOUND.value());
    }
}
