package pl.mareczek100.infrastructure.database.entityMapper;

import pl.mareczek100.domain.CarServiceHandling;
import pl.mareczek100.infrastructure.database.entity.CarServiceHandlingEntity;

public interface CarServiceHandlingEntityMapper {
    CarServiceHandling mapFromEntity(CarServiceHandlingEntity carServiceHandlingEntity);
    CarServiceHandlingEntity mapToEntity(CarServiceHandling carServiceHandling);

}
