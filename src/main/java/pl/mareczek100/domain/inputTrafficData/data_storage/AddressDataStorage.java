package pl.mareczek100.domain.inputTrafficData.data_storage;

import lombok.Value;
import org.springframework.stereotype.Repository;
import pl.mareczek100.domain.Address;

@Value
@Repository
public class AddressDataStorage {
    TrafficData trafficData;

    public Address createAddress(String email) {
        return trafficData.getCustomerBuyingList().stream()
                .filter(invoiceParameter -> invoiceParameter.contains(email))
                .map(string -> string.split(";"))
                .map(arr -> Address.builder()
                        .country(arr[4])
                        .city(arr[5])
                        .postalCode(arr[6])
                        .street(arr[7])
                        .buildingFlatNumber(arr[8])
                        .build())
                .toList().get(0);
    }

}
