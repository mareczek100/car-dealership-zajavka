package pl.mareczek100.service;

import lombok.Value;
import org.springframework.stereotype.Service;
import pl.mareczek100.infrastructure.data_storage.AddressDataStorage;
import pl.mareczek100.infrastructure.database.entity.AddressEntity;
import pl.mareczek100.service.dao.AddressRepository;

import java.util.List;

@Value
@Service
public class AddressService {
    AddressDataStorage addressDataStorage;
    AddressRepository addressRepository;

    public AddressEntity findCustomerAddress(String email) {
        return addressRepository.findCustomerAddress(email)
                .orElseThrow(() -> new RuntimeException("No such customer [%s]!".formatted(email)));
    }

    public List<AddressEntity> findAllAddresses() {
        List<AddressEntity> allAddressEntities = addressRepository.findAllAddresses();
        if (allAddressEntities.isEmpty()){
            throw  new RuntimeException("No address's at all!");
        }
        return allAddressEntities;
    }

    public AddressEntity createAddressForCustomer(String email) {
        return addressDataStorage.createAddress(email);
    }

    public void insertAddress(final AddressEntity addressEntity) {
        addressRepository.insertAddress(addressEntity);
    }

    public void initAddress() {
        addressDataStorage.simulationOfAddressesBase().forEach(this::insertAddress);
    }

}
