package pl.mareczek100.domain.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.mareczek100.domain.Customer;
import pl.mareczek100.infrastructure.database.entity.CustomerEntity;
import pl.mareczek100.infrastructure.database.entityMapper.CustomerEntityMapper;
import pl.mareczek100.infrastructure.database.jpaRepository.CustomerJpaRepository;
import pl.mareczek100.service.dao.CustomerRepository;

import java.util.List;
import java.util.Optional;


@Repository
@AllArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerEntityMapper customerMapper;

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
        CustomerEntity customerEntitySaved = customerJpaRepository.saveAndFlush(customerEntity);
        return customerMapper.mapFromEntity(customerEntitySaved);
    }


}
