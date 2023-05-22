package pl.mareczek100.service;

import lombok.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mareczek100.domain.Address;
import pl.mareczek100.service.dao.AddressRepository;

import java.util.List;

@Value
@Service
public class AddressService {
    AddressRepository addressRepository;

    @Transactional
    public Address findCustomerAddress(String email) {
        return addressRepository.findCustomerAddress(email)
                .orElseThrow(() -> new RuntimeException("No such customer [%s]!".formatted(email)));
    }
    @Transactional
    public List<Address> findAllAddresses() {
        List<Address> allAddressEntities = addressRepository.findAllAddresses();
        if (allAddressEntities.isEmpty()) {
            throw new RuntimeException("No address's at all!");
        }
        return allAddressEntities;
    }

}
