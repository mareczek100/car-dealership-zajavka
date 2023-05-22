package pl.mareczek100.service;

import lombok.Value;
import org.springframework.stereotype.Service;
import pl.mareczek100.domain.Customer;
import pl.mareczek100.domain.inputTrafficData.data_storage.PurchaseDataStorage;
import pl.mareczek100.service.dao.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Value
@Service
public class CustomerService {

    PurchaseDataStorage purchaseDataStorage;
    CustomerRepository customerRepository;

    public Customer findCustomer(String email) {
        return customerRepository.findCustomer(email)
                .orElseThrow(() -> new RuntimeException("No such customer [%s]!".formatted(email)));
    }

    public List<Customer> findAllCustomers() {
        List<Customer> allCustomerEntities = customerRepository.findAllCustomers();
        if (allCustomerEntities.isEmpty()){
            throw  new RuntimeException("No customer's at all!");
        }
        return allCustomerEntities;
    }

    public Customer createNewOrFindCustomerToMakeInvoice(String email) {
        Optional<Customer> optionalCustomer = customerRepository.findCustomer(email);
        if (optionalCustomer.isPresent()) {
            return optionalCustomer.get();
        }else {
        insertCustomer(purchaseDataStorage.customerWithAddress().get(0));
        return findCustomer(email);}
    }

    public void insertCustomer(Customer customer) {
        customerRepository.insertCustomer(customer);
    }

}
