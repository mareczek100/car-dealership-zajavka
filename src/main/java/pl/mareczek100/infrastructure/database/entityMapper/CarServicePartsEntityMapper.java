package pl.mareczek100.infrastructure.database.entityMapper;

import pl.mareczek100.domain.CarServiceParts;
import pl.mareczek100.infrastructure.database.entity.CarServicePartsEntity;


public interface CarServicePartsEntityMapper {
    CarServiceParts mapFromEntity(CarServicePartsEntity carServicePartsEntity);

    CarServicePartsEntity mapToEntity(CarServiceParts carServiceParts);

}
