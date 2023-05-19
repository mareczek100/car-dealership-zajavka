package pl.mareczek100.service.dao;

import pl.mareczek100.infrastructure.database.entity.Address;

import java.util.List;
import java.util.Optional;
public interface AddressRepository {

    Optional<Address> findCustomerAddress(String email);

    void insertAddress(Address address);

    List<Address> findAllAddresses();

}
