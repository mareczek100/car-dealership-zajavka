package pl.mareczek100.service.dao;

import pl.mareczek100.domain.Customer;

import java.util.List;
import java.util.Optional;


public interface CustomerRepository {

    Customer insertCustomer(Customer customer);
    Optional<Customer> findCustomer(String email);

    List<Customer> findAllCustomers();
}
