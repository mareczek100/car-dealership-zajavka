package pl.mareczek100.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mareczek100.domain.Address;
import pl.mareczek100.domain.Customer;
import pl.mareczek100.service.dao.CustomerRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AddressService addressService;

    @Transactional
    public Customer findCustomer(String email) {
        return customerRepository.findCustomer(email)
                .orElseThrow(() -> new RuntimeException(
                        "No such customer [%s]. Create an account first!".formatted(email)));
    }

    @Transactional
    public List<Customer> findAllCustomers() {
        List<Customer> allCustomer = customerRepository.findAllCustomers();
        if (allCustomer.isEmpty()) {
            throw new RuntimeException("No customer's at all!");
        }
        return allCustomer;
    }

    @Transactional
    public Customer insertCustomer(Customer customer) {
        if (checkIfCustomerExistInDataBaseDuringCreateNewAccount(customer.getEmail()).isPresent()) {
            throw new RuntimeException("""
                    Customer with email: [%s] exist!!
                    If You are not [%s] [%s] contact us immediately!
                    """.formatted(customer.getEmail(), customer.getName(), customer.getSurname()));
        }
        Optional<Address> addressFromDataBase = checkIfCustomerAddressExistInDataBase(customer.getAddress());
        if (addressFromDataBase.isPresent()){
            customer = customer.withAddress(addressFromDataBase.get());
        }
        return customerRepository.insertCustomer(customer);
    }

    private Optional<Customer> checkIfCustomerExistInDataBaseDuringCreateNewAccount(String email) {
        return findCustomersWithoutException().stream()
                .filter(cust -> cust.getEmail().equalsIgnoreCase(email))
                .findAny();
    }
    private Optional<Address> checkIfCustomerAddressExistInDataBase(Address address) {
        return addressService.findAllAddresses().stream()
                .filter(add -> add.equals(address))
                .findAny();
    }

    private List<Customer> findCustomersWithoutException() {
        List<Customer> allCustomer = customerRepository.findAllCustomers();
        if (allCustomer.isEmpty()) {
           return Collections.emptyList();
        }
        return allCustomer;
    }

}
