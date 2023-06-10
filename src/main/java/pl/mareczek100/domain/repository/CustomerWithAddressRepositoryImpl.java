package pl.mareczek100.domain.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.mareczek100.domain.Address;
import pl.mareczek100.domain.Customer;
import pl.mareczek100.infrastructure.database.entity.AddressEntity;
import pl.mareczek100.infrastructure.database.entity.CustomerEntity;
import pl.mareczek100.infrastructure.database.entityMapper.AddressEntityMapper;
import pl.mareczek100.infrastructure.database.entityMapper.CustomerEntityMapper;
import pl.mareczek100.infrastructure.database.jpaRepository.AddressJpaRepository;
import pl.mareczek100.infrastructure.database.jpaRepository.CustomerJpaRepository;
import pl.mareczek100.service.dao.AddressRepository;
import pl.mareczek100.service.dao.CustomerRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
@AllArgsConstructor
public class CustomerWithAddressRepositoryImpl implements CustomerRepository, AddressRepository {

    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerEntityMapper customerMapper;
    private final AddressJpaRepository addressJpaRepository;
    private final AddressEntityMapper addressMapper;

    @Override
    public Optional<Customer> findCustomer(String email) {
        return customerJpaRepository.findByEmail(email)
                .map(customerMapper::mapFromEntity);
    }

    @Override
    public List<Customer> findAllCustomers() {
        return customerJpaRepository.findAll().stream()
                .map(customerMapper::mapFromEntity)
                .toList();
    }

    @Override
    public Customer insertCustomer(Customer customer) {
        CustomerEntity customerEntity = customerMapper.mapToEntity(customer);
        CustomerEntity customerEntitySaved = null;
        Address customerAddress = customer.getAddress();

        if (Objects.isNull(customerAddress.getAddressId())){
            AddressEntity addressEntity = addressMapper.mapToEntity(customerAddress);
            addressJpaRepository.saveAndFlush(addressEntity);
            customerEntitySaved = customerJpaRepository.saveAndFlush(customerEntity);
        }
        if (Objects.nonNull(customerAddress.getAddressId())){
            customerEntitySaved = customerJpaRepository.saveAndFlush(customerEntity);
        }

        return customerMapper.mapFromEntity(customerEntitySaved);
    }

    public Optional<Address> findCustomerAddress(String email) {
        return addressJpaRepository.findCustomerAddress(email)
                .map(addressMapper::mapFromEntity);
    }
    @Override
    public List<Address> findAllAddresses() {
        return addressJpaRepository.findAll().stream()
                .map(addressMapper::mapFromEntity)
                .toList();
    }


}
