package pl.mareczek100.infrastructure.database.entityMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.mareczek100.domain.CarServiceParts;
import pl.mareczek100.infrastructure.database.entity.CarServicePartsEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarServicePartsEntityMapper {
    @Mapping(target = "carServiceRequest", ignore = true)
    CarServiceParts mapFromEntity(CarServicePartsEntity carServicePartsEntity);
//    @Mapping(target = "carServiceRequest.carServicePart", ignore = true)
//    @Mapping(target = "carServiceRequest.customer", ignore = true)
//    @Mapping(target = "carServiceRequest.carServiceHandling", ignore = true)
//    @Mapping(target = "carServiceRequest.carToService", ignore = true)
    CarServicePartsEntity mapToEntity(CarServiceParts carServiceParts);

}