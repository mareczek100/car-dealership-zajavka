package pl.mareczek100.infrastructure.data_storage;

import lombok.Value;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.database.entity.AddressEntity;

import java.util.ArrayList;
import java.util.List;

@Value
@Repository
public class AddressDataStorage {
    TrafficData trafficData;

    public AddressEntity createAddress(String email) {
        return trafficData.getCustomerBuyingList().stream()
                .filter(invoiceParameter -> invoiceParameter.contains(email))
                .map(string -> string.split(";"))
                .map(arr -> AddressEntity.builder()
                        .country(arr[4])
                        .city(arr[5])
                        .postalCode(arr[6])
                        .street(arr[7])
                        .buildingFlatNumber(arr[8])
                        .build())
                .toList().get(0);
    }

    public List<AddressEntity> simulationOfAddressesBase() {
        List<AddressEntity> addressEntityList = new ArrayList<>();
        addressEntityList.add(AddressEntity.builder()
                .country("Polska")
                .city("Wrocław")
                .postalCode("20-001")
                .street("Bokserska")
                .buildingFlatNumber("15")
                .build());
        addressEntityList.add(AddressEntity.builder()
                .country("Polska")
                .city("Kołobrzeg")
                .postalCode("78-100")
                .street("Kupiecka")
                .buildingFlatNumber("10/32")
                .build());
        addressEntityList.add(AddressEntity.builder()
                .country("Polska")
                .city("Kołobrzeg")
                .postalCode("78-100")
                .street("Rybacka")
                .buildingFlatNumber("99")
                .build());
        addressEntityList.add(AddressEntity.builder()
                .country("Polska")
                .city("Wrocław")
                .postalCode("30-001")
                .street("Wesoła")
                .buildingFlatNumber("24")
                .build());

        return addressEntityList;
    }

}
