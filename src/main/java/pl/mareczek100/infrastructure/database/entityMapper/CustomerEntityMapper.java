package pl.mareczek100.infrastructure.database.entityMapper;

import pl.mareczek100.domain.Customer;
import pl.mareczek100.infrastructure.database.entity.CustomerEntity;

public interface CustomerEntityMapper {
    Customer mapFromEntity(CustomerEntity customerEntity);
    CustomerEntity mapToEntity(Customer customer);


}
