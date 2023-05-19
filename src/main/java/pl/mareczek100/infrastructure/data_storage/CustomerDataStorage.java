package pl.mareczek100.infrastructure.data_storage;

import lombok.Value;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.database.entity.Address;
import pl.mareczek100.infrastructure.database.entity.Customer;
import pl.mareczek100.service.AddressService;

import java.util.List;

@Value
@Repository
public class CustomerDataStorage {

    TrafficData trafficData;
    AddressService addressService;

    public Customer createCustomerWithAddress(String email) {
        return trafficData.getCustomerBuyingList().stream()
                .filter(invoiceParameter -> invoiceParameter.contains(email))
                .map(string -> string.split(";"))
                .map(arr -> Customer.builder()
                        .name(arr[0])
                        .surname(arr[1])
                        .phone(arr[2])
                        .email(arr[3])
                        .address(getAddressForCustomer(email))
                        .build())
                .toList().get(0);
    }

    private Address getAddressForCustomer(String email) {
        Address customerAddress = addressService.createAddressForCustomer(email);
        List<Address> allAddresses = addressService.findAllAddresses();

        if (allAddresses.contains(customerAddress)) {
            int existingAddress = allAddresses.indexOf(customerAddress);
            return allAddresses.get(existingAddress);
        }
        return customerAddress;
    }


}