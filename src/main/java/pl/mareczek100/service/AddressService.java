package pl.mareczek100.service;

import lombok.Value;
import org.springframework.stereotype.Service;
import pl.mareczek100.domain.Address;
import pl.mareczek100.domain.inputTrafficData.data_storage.AddressDataStorage;
import pl.mareczek100.service.dao.AddressRepository;

import java.util.List;

@Value
@Service
public class AddressService {
    AddressDataStorage addressDataStorage;
    AddressRepository addressRepository;

    public Address findCustomerAddress(String email) {
        return addressRepository.findCustomerAddress(email)
                .orElseThrow(() -> new RuntimeException("No such customer [%s]!".formatted(email)));
    }

    public List<Address> findAllAddresses() {
        List<Address> allAddressEntities = addressRepository.findAllAddresses();
        if (allAddressEntities.isEmpty()){
            throw  new RuntimeException("No address's at all!");
        }
        return allAddressEntities;
    }

    public Address createAddressForCustomer(String email) {
        return addressDataStorage.createAddress(email);
    }

}
