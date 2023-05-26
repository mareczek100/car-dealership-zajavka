package pl.mareczek100.domain.inputTrafficData.data_storage;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.mareczek100.domain.Address;
import pl.mareczek100.domain.CarServiceRequest;
import pl.mareczek100.domain.Customer;
import pl.mareczek100.service.CarToServiceService;
import pl.mareczek100.service.CustomerService;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
@Repository
@AllArgsConstructor
public class CarServiceRequestDataStorage {

    private final TrafficData trafficData;
    private final CustomerService customerService;
    private final CarToServiceService carToServiceService;

    public List<CarServiceRequest> createInnerCarServiceRequest() {

        return trafficData.getCarFromDealerServiceRequestList().stream()
                .map(string -> string.split(";"))
                .map(arr -> CarServiceRequest.builder()
                        .carServiceRequestNumber("" + UUID.randomUUID())
                        .receivedDateTime(OffsetDateTime.now())
                        .comment(arr[2])
                        .customer(customerService.findCustomer(arr[0]))
                        .carToService(carToServiceService.carToServiceInit(arr[1]))
                        .build())
                .toList();
    }

    public List<CarServiceRequest> createOuterCarServiceRequest() {

        return trafficData.getCarOuterServiceRequestList().stream()
                .map(string -> string.split(";"))
                .map(arr -> CarServiceRequest.builder()
                        .carServiceRequestNumber("" + UUID.randomUUID())
                        .receivedDateTime(OffsetDateTime.now())
                        .comment(arr[5])
                        .customer(customerService.insertCustomer(getNewCustomer()))
                        .carToService(carToServiceService.carToServiceInit(arr[1]))
                        .build())
                .toList();
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