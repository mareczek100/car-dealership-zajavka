package pl.mareczek100.service.dao;

import pl.mareczek100.infrastructure.database.entity.Customer;

import java.util.List;
import java.util.Optional;


public interface CustomerRepository {

    void insertCustomer(Customer customer);
    Optional<Customer> findCustomer(String email);

    List<Customer> findAllCustomers();
}
