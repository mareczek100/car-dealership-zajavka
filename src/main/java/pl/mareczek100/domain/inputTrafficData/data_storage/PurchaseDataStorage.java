package pl.mareczek100.domain.inputTrafficData.data_storage;

import lombok.Value;
import org.springframework.stereotype.Repository;
import pl.mareczek100.domain.Address;
import pl.mareczek100.domain.Customer;
import pl.mareczek100.service.AddressService;

import java.util.List;

@Value
@Repository
public class PurchaseDataStorage {
    TrafficData trafficData;
    AddressService addressService;

    public List<Customer> customerWithAddress() {
        return trafficData.getCustomerBuyingList().stream()
                .map(string -> string.split(";"))
                .map(arr -> Customer.builder()
                        .name(arr[0])
                        .surname(arr[1])
                        .phone(arr[2])
                        .email(arr[3])
                        .address(getAddressForCustomer(arr[3]))
                        .build())
                .toList();
    }

    public List<String[]> vinAndPeselFirstTime(){
        return trafficData.getInvoiceList().stream()
                .map(string -> string.split(";"))
                .toList();
    }
    public String[] emailVinAndPeselAgain(){
        return trafficData.getInvoiceListAgain().stream()
                .map(string -> string.split(";"))
                .toList().get(0);
    }

    private Address getAddressForCustomer(String email) {
        Address customerAddress = createAddress(email);
        List<Address> allAddresses = addressService.findAllAddresses();

        if (allAddresses.contains(customerAddress)) {
            int existingAddress = allAddresses.indexOf(customerAddress);
            return allAddresses.get(existingAddress);
        }
        return customerAddress;
    }
    private Address createAddress(String email) {
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