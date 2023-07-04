package pl.mareczek100.infrastructure.configuration.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import pl.mareczek100.infrastructure.cepik.ApiClient;
import pl.mareczek100.infrastructure.cepik.api.PojazdyApi;
import pl.mareczek100.infrastructure.cepik.api.SownikiApi;

@Configuration
public class WebClientConfig {

    @Value("${api.cepik.url}")
    private String CEPIK_URL;

    @Bean
    public WebClient webClient(ObjectMapper objectMapper) {
        final var strategies = ExchangeStrategies.builder()
                .codecs(clientCodecConfigurer -> {
                    clientCodecConfigurer
                            .defaultCodecs()
                            .jackson2JsonEncoder(
                                    new Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON)
                            );
                    clientCodecConfigurer
                            .defaultCodecs()
                            .jackson2JsonDecoder(
                                    new Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON)
                            );
                }).build();
        return WebClient.builder()
                .exchangeStrategies(strategies)
                .build();
    }

    @Bean
    public ApiClient apiClient(final WebClient webClient) {
        ApiClient apiClient = new ApiClient(webClient);
        apiClient.setBasePath(CEPIK_URL);
        return apiClient;
    }

    @Bean
    public PojazdyApi pojazdyApi(final ApiClient apiClient) {
        return new PojazdyApi(apiClient);
    }

    @Bean
    public SownikiApi sownikiApi(final ApiClient apiClient) {
        return new SownikiApi(apiClient);
    }

}
