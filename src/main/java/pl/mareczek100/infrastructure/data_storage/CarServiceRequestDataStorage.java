package pl.mareczek100.infrastructure.data_storage;

import lombok.Value;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.database.entity.Address;
import pl.mareczek100.infrastructure.database.entity.CarServiceRequest;
import pl.mareczek100.infrastructure.database.entity.Customer;
import pl.mareczek100.service.CarToServiceService;
import pl.mareczek100.service.CustomerService;

import java.time.OffsetDateTime;
import java.util.UUID;

@Value
@Repository
public class CarServiceRequestDataStorage {
    TrafficData trafficData;
    CustomerService customerService;
    CarToServiceService carToServiceService;

    public CarServiceRequest createCarServiceRequest(String email) {

        if (customerService.findAllCustomers().stream()
                .anyMatch(customer -> email.equalsIgnoreCase(customer.getEmail()))) {

            return trafficData.getCarFromDealerServiceRequestList().stream()
                    .filter(string -> string.contains(email))
                    .map(string -> string.split(";"))
                    .map(arr -> CarServiceRequest.builder()
                            .carServiceRequestNumber(email + UUID.randomUUID())
                            .receivedDateTime(OffsetDateTime.now())
                            .customerComment(arr[2])
                            .customer(customerService.findCustomer(arr[0]))
                            .carToService(carToServiceService.carToServiceInit(arr[1]))
                            .build())
                    .toList().get(0);
        } else {

            return trafficData.getCarOuterServiceRequestList().stream()
                    .filter(string -> string.contains(email))
                    .map(string -> string.split(";"))
                    .map(arr -> CarServiceRequest.builder()
                            .carServiceRequestNumber(email + OffsetDateTime.now().toEpochSecond())
                            .receivedDateTime(OffsetDateTime.now())
                            .customerComment(arr[5])
                            .customer(getNewCustomer())
                            .carToService(carToServiceService.carToServiceInit(arr[1]))
                            .build())
                    .toList().get(0);
        }
    }

    private Customer getNewCustomer() {
        return trafficData.getCustomerOuterList().stream()
                .map(line -> line.split(";"))
                .map(arr -> Customer.builder()
                        .name(arr[0])
                        .surname(arr[1])
                        .phone(arr[2])
                        .email(arr[3])
                        .address(Address.builder()
                                .country(arr[4])
                                .city(arr[5])
                                .postalCode(arr[6])
                                .street(arr[7])
                                .buildingFlatNumber(arr[8])
                                .build())
                        .build())
                .toList().get(0);
    }

}