package pl.mareczek100.service.dao;

import pl.mareczek100.infrastructure.database.entity.AddressEntity;

import java.util.List;
import java.util.Optional;
public interface AddressRepository {

    Optional<AddressEntity> findCustomerAddress(String email);

    void insertAddress(AddressEntity addressEntity);

    List<AddressEntity> findAllAddresses();

}
