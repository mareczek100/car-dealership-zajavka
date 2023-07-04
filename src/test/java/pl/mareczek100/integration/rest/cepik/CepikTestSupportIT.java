package pl.mareczek100.integration.rest.cepik;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;
import pl.mareczek100.api.cepik.cepik_dto.CepikVehicleDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface CepikTestSupportIT {
    RequestSpecification requestSpecification();

    default List<CepikVehicleDTO> findCepikVehiclesByDateFromAndDateTo(final LocalDate dateFrom, final LocalDate dateTo){
        Map<String, String> params = Map.of(
                "dateFrom", dateFrom.toString(),
                "dateTo", dateTo.toString()
        );
        return requestSpecification()
                .given()
                .params(params)
                .when()
                .get(CepikIT.CEPIK_HOME + "/vehicles")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(List.class);
    }
    default CepikVehicleDTO findCepikVehicleByIdAndDate(final String cepikVehicleId, final LocalDate dateFrom){
        return requestSpecification()
                .given()
                .queryParam("cepikVehicleId", cepikVehicleId)
                .queryParam("dateFrom", dateFrom.toString())
                .when()
                .get(CepikIT.CEPIK_HOME + "/vehicle")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(CepikVehicleDTO.class);
    }
}
