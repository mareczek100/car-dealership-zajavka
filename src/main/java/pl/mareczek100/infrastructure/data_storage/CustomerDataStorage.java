package pl.mareczek100.infrastructure.data_storage;

import lombok.Value;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.database.entity.AddressEntity;
import pl.mareczek100.infrastructure.database.entity.CustomerEntity;
import pl.mareczek100.service.AddressService;

import java.util.List;

@Value
@Repository
public class CustomerDataStorage {

    TrafficData trafficData;
    AddressService addressService;

    public CustomerEntity createCustomerWithAddress(String email) {
        return trafficData.getCustomerBuyingList().stream()
                .filter(invoiceParameter -> invoiceParameter.contains(email))
                .map(string -> string.split(";"))
                .map(arr -> CustomerEntity.builder()
                        .name(arr[0])
                        .surname(arr[1])
                        .phone(arr[2])
                        .email(arr[3])
                        .addressEntity(getAddressForCustomer(email))
                        .build())
                .toList().get(0);
    }

    private AddressEntity getAddressForCustomer(String email) {
        AddressEntity customerAddressEntity = addressService.createAddressForCustomer(email);
        List<AddressEntity> allAddressEntities = addressService.findAllAddresses();

        if (allAddressEntities.contains(customerAddressEntity)) {
            int existingAddress = allAddressEntities.indexOf(customerAddressEntity);
            return allAddressEntities.get(existingAddress);
        }
        return customerAddressEntity;
    }


}