package pl.mareczek100.infrastructure.database.entityMapper;

import pl.mareczek100.domain.Address;
import pl.mareczek100.infrastructure.database.entity.AddressEntity;

public interface AddressEntityMapper {
    Address mapFromEntity(AddressEntity addressEntity);
    AddressEntity mapToEntity(Address address);

}