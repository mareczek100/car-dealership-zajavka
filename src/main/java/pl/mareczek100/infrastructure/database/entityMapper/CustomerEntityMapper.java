package pl.mareczek100.infrastructure.database.entityMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.mareczek100.domain.Customer;
import pl.mareczek100.infrastructure.database.entity.CustomerEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerEntityMapper {

    @Mapping(target = "carServiceRequests", ignore = true)
    @Mapping(target = "invoices", ignore = true)
    @Mapping(target = "address.customers", ignore = true)
    Customer mapFromEntity(CustomerEntity customerEntity);

    @Mapping(target = "carServiceRequests", ignore = true)
    @Mapping(target = "invoices", ignore = true)
    @Mapping(target = "address.customers", ignore = true)
    CustomerEntity mapToEntity(Customer customer);

}
