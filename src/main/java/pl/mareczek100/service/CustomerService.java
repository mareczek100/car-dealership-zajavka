package pl.mareczek100.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mareczek100.domain.Customer;
import pl.mareczek100.domain.inputTrafficData.data_storage.PurchaseDataStorage;
import pl.mareczek100.service.dao.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {

    private final PurchaseDataStorage purchaseDataStorage;
    private final CustomerRepository customerRepository;
    @Transactional
    public Customer findCustomer(String email) {
        return customerRepository.findCustomer(email)
                .orElseThrow(() -> new RuntimeException("No such customer [%s]!".formatted(email)));
    }
    @Transactional
    public List<Customer> findAllCustomers() {
        List<Customer> allCustomerEntities = customerRepository.findAllCustomers();
        if (allCustomerEntities.isEmpty()){
            throw  new RuntimeException("No customer's at all!");
        }
        return allCustomerEntities;
    }
    @Transactional
    public Customer createNewOrFindCustomerToMakeInvoice(String email) {
        Optional<Customer> optionalCustomer = customerRepository.findCustomer(email);
        if (optionalCustomer.isPresent()) {
            return optionalCustomer.get();
        }else {
        insertCustomer(purchaseDataStorage.customerWithAddress(email));
        return findCustomer(email);}
    }
    @Transactional
    public Customer insertCustomer(Customer customer) {
        return customerRepository.insertCustomer(customer);
    }

}
