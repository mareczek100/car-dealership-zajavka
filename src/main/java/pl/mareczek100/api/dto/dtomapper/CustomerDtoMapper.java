package pl.mareczek100.api.dto.dtomapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.mareczek100.api.dto.CustomerDTO;
import pl.mareczek100.domain.Customer;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerDtoMapper {

    CustomerDTO mapToDTO(Customer customer);
    Customer mapFromDTO(CustomerDTO customerDTO);

}
