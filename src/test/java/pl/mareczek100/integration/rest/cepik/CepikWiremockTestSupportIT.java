package pl.mareczek100.integration.rest.cepik;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.matching.StringValuePattern;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Map;
import java.util.Objects;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public interface CepikWiremockTestSupportIT {

    default void stubForGetSlowniki(WireMockServer wireMockServer) {
        wireMockServer.stubFor(
                get(urlPathMatching("/slowniki/wojewodztwa"))
                        .willReturn(aResponse()
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("wiremock/slowniki-wojewodztwa.json")));
    }

    default void stubForGetPojazdy(WireMockServer wireMockServer, String dateFrom, String dateTo) {
        Map<String, StringValuePattern> queryParamsPattern;

        if (Objects.isNull(dateTo)) {
            queryParamsPattern = Map.of(
                    "data-od", equalTo(dateFrom.replace("-", ""))
            );
        } else {
           queryParamsPattern = Map.of(
                    "data-od", equalTo(dateFrom.replace("-", "")),
                    "data-do", equalTo(dateTo.replace("-", ""))
            );
        }

        wireMockServer.stubFor(get(urlPathEqualTo("/pojazdy"))
                .withQueryParams(queryParamsPattern)
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("wiremock/pojazdy.json")));
    }

    default void stubForGetPojazd1(WireMockServer wireMockServer, String cepikVehicleId) {
        wireMockServer.stubFor(get(urlPathEqualTo("/pojazdy/%s".formatted(cepikVehicleId)))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("wiremock/pojazdByCustomerId1ThisOrderFromList.json")));
    }

    default void stubForGetPojazd2(WireMockServer wireMockServer, String cepikVehicleId) {
        wireMockServer.stubFor(get(urlPathEqualTo("/pojazdy/%s".formatted(cepikVehicleId)))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("wiremock/pojazdByCustomerId2ThisOrderFromList.json")));
    }

    default void stubForGetPojazd3(WireMockServer wireMockServer, String cepikVehicleId) {
        wireMockServer.stubFor(get(urlPathEqualTo("/pojazdy/%s".formatted(cepikVehicleId)))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("wiremock/pojazdByCustomerId7ThisOrderFromList.json")));
    }
}
