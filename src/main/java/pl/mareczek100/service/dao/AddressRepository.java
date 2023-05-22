package pl.mareczek100.service.dao;

import pl.mareczek100.domain.Address;

import java.util.List;
import java.util.Optional;
public interface AddressRepository {
    Optional<Address> findCustomerAddress(String email);
    List<Address> findAllAddresses();

}
