package pl.mareczek100.infrastructure.database.entityMapper;

import pl.mareczek100.domain.CarToService;
import pl.mareczek100.infrastructure.database.entity.CarToServiceEntity;

public interface CarToServiceEntityMapper {
    CarToService mapFromEntity(CarToServiceEntity carToServiceEntity);
    CarToServiceEntity mapToEntity(CarToService carToService);

}