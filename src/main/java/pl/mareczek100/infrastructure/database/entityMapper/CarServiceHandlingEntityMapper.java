package pl.mareczek100.infrastructure.database.entityMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.mareczek100.domain.CarServiceHandling;
import pl.mareczek100.infrastructure.database.entity.CarServiceHandlingEntity;
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarServiceHandlingEntityMapper {
    @Mapping(target = "carServiceRequest", ignore = true)
    CarServiceHandling mapFromEntity(CarServiceHandlingEntity carServiceHandlingEntity);

    @Mapping(target = "carServiceRequest.carServiceHandling", ignore = true)
    CarServiceHandlingEntity mapToEntity(CarServiceHandling carServiceHandling);

}
