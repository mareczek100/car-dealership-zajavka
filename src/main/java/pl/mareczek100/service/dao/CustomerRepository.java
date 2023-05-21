package pl.mareczek100.service.dao;

import pl.mareczek100.infrastructure.database.entity.CustomerEntity;

import java.util.List;
import java.util.Optional;


public interface CustomerRepository {

    void insertCustomer(CustomerEntity customerEntity);
    Optional<CustomerEntity> findCustomer(String email);

    List<CustomerEntity> findAllCustomers();
}
