package pl.mareczek100.infrastructure.database.entityMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.mareczek100.domain.CarToService;
import pl.mareczek100.infrastructure.database.entity.CarToServiceEntity;
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarToServiceEntityMapper {
    @Mapping(target = "carServiceRequests", ignore = true)
    CarToService mapFromEntity(CarToServiceEntity carToServiceEntity);
    @Mapping(target = "carServiceRequests", ignore = true)
    CarToServiceEntity mapToEntity(CarToService carToService);

}