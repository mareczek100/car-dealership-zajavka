package pl.mareczek100.infrastructure.database.entityMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.mareczek100.domain.CarServiceRequest;
import pl.mareczek100.infrastructure.database.entity.CarServiceRequestEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarServiceRequestEntityMapper {
    @Mapping(target = "carServiceHandling", ignore = true)
    @Mapping(target = "carServicePart", ignore = true)
    CarServiceRequest mapFromEntity(CarServiceRequestEntity carServiceRequestEntity);
    CarServiceRequestEntity mapToEntity(CarServiceRequest carServiceRequest);


}
