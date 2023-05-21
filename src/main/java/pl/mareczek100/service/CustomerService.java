package pl.mareczek100.service;

import lombok.Value;
import org.springframework.stereotype.Service;
import pl.mareczek100.infrastructure.data_storage.CustomerDataStorage;
import pl.mareczek100.infrastructure.database.entity.CustomerEntity;
import pl.mareczek100.service.dao.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Value
@Service
public class CustomerService {

    CustomerDataStorage customerDataStorage;
    CustomerRepository customerRepository;

    public CustomerEntity findCustomer(String email) {
        return customerRepository.findCustomer(email)
                .orElseThrow(() -> new RuntimeException("No such customer [%s]!".formatted(email)));
    }

    public List<CustomerEntity> findAllCustomers() {
        List<CustomerEntity> allCustomerEntities = customerRepository.findAllCustomers();
        if (allCustomerEntities.isEmpty()){
            throw  new RuntimeException("No customer's at all!");
        }
        return allCustomerEntities;
    }

    public CustomerEntity createNewOrFindCustomerToMakeInvoice(String email) {
        Optional<CustomerEntity> optionalCustomer = customerRepository.findCustomer(email);
        if (optionalCustomer.isPresent()) {
            return optionalCustomer.get();
        }else {
        insertCustomer(customerDataStorage.createCustomerWithAddress(email));
        return findCustomer(email);}
    }

    public void insertCustomer(CustomerEntity customerEntity) {
        customerRepository.insertCustomer(customerEntity);
    }

}
