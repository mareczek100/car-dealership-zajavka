package pl.mareczek100.integration.rest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.mareczek100.api.dto.CarDTO;
import pl.mareczek100.api.dto.InvoiceDTO;
import pl.mareczek100.config.CarPurchaseTestSupportIT;
import pl.mareczek100.config.RestAssuredIntegrationTestConfig;
import pl.mareczek100.domain.Customer;
import pl.mareczek100.service.CustomerService;
import pl.mareczek100.test_data_storage.TestInputData;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CarPurchaseIT extends RestAssuredIntegrationTestConfig implements CarPurchaseTestSupportIT {

    private final CustomerService customerService;
    @Test
    void setUp() {
        Assertions.assertNotNull(customerService);
    }

    @Test
    void thatCarPurchaseWorksCorrectly() {
        //given
        String carVin = "1FT7X2B60FEA74019";
        InvoiceDTO invoiceDTO;
        Customer customerToInsert = TestInputData.customerToInsert();

        Optional<Customer> existedCustomer = customerService.findAllCustomers().stream()
                .filter(cust -> cust.getEmail().equals(customerToInsert.getEmail()))
                .findAny();
        CarDTO availableCarToSell = findAvailableCar(carVin);

        //when
        List<CarDTO> allAvailableCarsBefore = findAvailableCars();
        if (existedCustomer.isPresent()) {
            invoiceDTO = purchaseCar(existedCustomer.get().getEmail(), availableCarToSell.vin());
        } else {
            Customer insertedCustomer = customerService.insertCustomer(customerToInsert);
            invoiceDTO = purchaseCar(availableCarToSell.vin(), insertedCustomer.getEmail());
        }

        //then
        List<CarDTO> allAvailableCarsAfter = findAvailableCars();
        org.assertj.core.api.Assertions.assertThat(invoiceDTO.invoiceNumber()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(invoiceDTO.dateTime()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(allAvailableCarsBefore.size() - 1)
                .isEqualTo(allAvailableCarsAfter.size());

    }


}
