package pl.mareczek100.config;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;
import pl.mareczek100.api.dto.CarDTO;
import pl.mareczek100.api.dto.InvoiceDTO;

import java.util.List;

public interface CarPurchaseTestSupportIT {
    String CAR_PURCHASE_HOME = "/api/car";
    String CAR_PURCHASE = "/api/car/purchase/{carVin}";

    RequestSpecification requestSpecification();

    default List<CarDTO> findAvailableCars() {
        return requestSpecification()
                .get(CAR_PURCHASE_HOME)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(List.class);
    }
    default CarDTO findAvailableCar(final String vin) {
        return requestSpecification()
                .pathParam("carVin", vin)
                .get(CAR_PURCHASE)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(CarDTO.class);
    }
    default InvoiceDTO purchaseCar(final String vin, final String email) {
        return requestSpecification()
                .given()
                .pathParam("carVin", vin)
                .queryParam("email", email)
                .accept(ContentType.JSON)
                .when()
                .post(CAR_PURCHASE)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(InvoiceDTO.class);
    }
}