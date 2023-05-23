package pl.mareczek100.infrastructure.database.entityMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.mareczek100.domain.CarServiceParts;
import pl.mareczek100.infrastructure.database.entity.CarServicePartsEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarServicePartsEntityMapper {
    @Mapping(target = "carServiceRequest", ignore = true)
    @Mapping(target = "part", ignore = true)
    CarServiceParts mapFromEntity(CarServicePartsEntity carServicePartsEntity);

    CarServicePartsEntity mapToEntity(CarServiceParts carServiceParts);

}
