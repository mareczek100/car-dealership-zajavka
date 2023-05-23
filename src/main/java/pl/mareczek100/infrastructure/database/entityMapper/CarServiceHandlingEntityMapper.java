package pl.mareczek100.infrastructure.database.entityMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.mareczek100.domain.CarServiceHandling;
import pl.mareczek100.infrastructure.database.entity.CarServiceHandlingEntity;
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarServiceHandlingEntityMapper {
    @Mapping(target = "carServiceRequest", ignore = true)
    @Mapping(target = "mechanic", ignore = true)
    @Mapping(target = "service", ignore = true)
    CarServiceHandling mapFromEntity(CarServiceHandlingEntity carServiceHandlingEntity);
    CarServiceHandlingEntity mapToEntity(CarServiceHandling carServiceHandling);

}
