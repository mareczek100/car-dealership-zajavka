package pl.mareczek100.infrastructure.database.entityMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.mareczek100.domain.Address;
import pl.mareczek100.infrastructure.database.entity.AddressEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressEntityMapper {
    @Mapping(target = "customers", ignore = true)
    Address mapFromEntity(AddressEntity addressEntity);
    @Mapping(target = "customers", ignore = true)
    AddressEntity mapToEntity(Address address);

}