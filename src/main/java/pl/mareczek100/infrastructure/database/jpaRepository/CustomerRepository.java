package pl.mareczek100.infrastructure.database.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mareczek100.infrastructure.database.entity.CustomerEntity;

import java.util.List;
import java.util.Optional;


public interface CustomerRepository extends JpaRepository<Object, Integer> {

    void insertCustomer(CustomerEntity customerEntity);
    Optional<CustomerEntity> findCustomer(String email);

    List<CustomerEntity> findAllCustomers();
}
