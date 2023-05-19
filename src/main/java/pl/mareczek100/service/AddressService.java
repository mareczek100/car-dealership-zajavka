package pl.mareczek100.service;

import lombok.Value;
import org.springframework.stereotype.Service;
import pl.mareczek100.infrastructure.data_storage.AddressDataStorage;
import pl.mareczek100.infrastructure.database.entity.Address;
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
        List<Address> allAddresses = addressRepository.findAllAddresses();
        if (allAddresses.isEmpty()){
            throw  new RuntimeException("No address's at all!");
        }
        return allAddresses;
    }

    public Address createAddressForCustomer(String email) {
        return addressDataStorage.createAddress(email);
    }

    public void insertAddress(final Address address) {
        addressRepository.insertAddress(address);
    }

    public void initAddress() {
        addressDataStorage.simulationOfAddressesBase().forEach(this::insertAddress);
    }

}
