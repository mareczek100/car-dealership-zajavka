package pl.mareczek100.integration.rest.cepik;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.mareczek100.api.cepik.cepik_dto.CepikVehicleDTO;
import pl.mareczek100.config.RestAssuredIntegrationTestConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CepikIT extends RestAssuredIntegrationTestConfig implements CepikTestSupportIT, CepikWiremockTestSupportIT {

    protected final static String CEPIK_HOME = "/api/cepik";
    protected final static Path POJAZDY_IDS = Paths.get("./src/test/resources/__files/wiremock/pojazdyId's.txt");
    protected final static Path POJAZDY_MASA = Paths.get("./src/test/resources/__files/wiremock/pojazdyMasa.txt");

    @Test
    void thatCepikVehicleListIsGetCorrectlyByDateFromAndDateTo() throws IOException {
        // given
        LocalDate dateFrom = LocalDate.of(2022, 1, 1);
        LocalDate dateTo = LocalDate.of(2022, 6, 30);
        List<String> pojazdyCepikId = Files.readAllLines(POJAZDY_IDS);
        List<String> pojazdyCepikMasa = Files.readAllLines(POJAZDY_MASA);
        stubForGetSlowniki(wireMockServer);
        stubForGetPojazdy(wireMockServer, dateFrom.toString(), dateTo.toString());

        //when
        List<CepikVehicleDTO> cepikVehiclesByDateFromAndDateTo = findCepikVehiclesByDateFromAndDateTo(dateFrom, dateTo);
        ObjectMapper objectMapper = getObjectMapper();

        //then
        Assertions.assertThat(cepikVehiclesByDateFromAndDateTo).isNotEmpty();
        Assertions.assertThat(cepikVehiclesByDateFromAndDateTo.size()).isEqualTo(pojazdyCepikId.size());
        pojazdyCepikId.forEach(cepikVehicleID -> {
            try {
                Assertions.assertThat(
                        objectMapper.writeValueAsString(cepikVehiclesByDateFromAndDateTo)).contains(pojazdyCepikMasa);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void thatCepikVehicleIsGetCorrectlyByDateAndRandomIdGivenFromCustomer() {
        // given
        String cepikVehicleId1 = "2334695168647808";
        String cepikVehicleCustomerOrderedId1 = "1";
        String cepikVehicleCustomerOrderedId1Model = "ASTRA STATION WAGON";
        String cepikVehicleId2 = "4678426239814328";
        String cepikVehicleCustomerOrderedId2 = "2";
        String cepikVehicleCustomerOrderedId2Model = "XC60";
        String cepikVehicleId7 = "7598480131081314";
        String cepikVehicleCustomerOrderedId7 = "7";
        String cepikVehicleCustomerOrderedId7Model = "---";
        LocalDate dateFrom = LocalDate.of(2022, 1, 1);

        stubForGetSlowniki(wireMockServer);
        stubForGetPojazdy(wireMockServer, dateFrom.toString(), null);
        stubForGetPojazd1(wireMockServer, cepikVehicleId1);
        stubForGetPojazd2(wireMockServer, cepikVehicleId2);
        stubForGetPojazd3(wireMockServer, cepikVehicleId7);

        //when
        CepikVehicleDTO cepikVehicleOrdered1 = findCepikVehicleByIdAndDate(cepikVehicleCustomerOrderedId1, dateFrom);
        CepikVehicleDTO cepikVehicleOrdered2 = findCepikVehicleByIdAndDate(cepikVehicleCustomerOrderedId2, dateFrom);
        CepikVehicleDTO cepikVehicleOrdered7 = findCepikVehicleByIdAndDate(cepikVehicleCustomerOrderedId7, dateFrom);

        //then
        Assertions.assertThat(cepikVehicleOrdered1.model())
                .isEqualTo(cepikVehicleCustomerOrderedId1Model);
        Assertions.assertThat(cepikVehicleOrdered2.model())
                .isEqualTo(cepikVehicleCustomerOrderedId2Model);
        Assertions.assertThat(cepikVehicleOrdered7.model())
                .isEqualTo(cepikVehicleCustomerOrderedId7Model);
    }

}
