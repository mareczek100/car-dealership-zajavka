package pl.mareczek100.infrastructure.database.entityMapper;

import pl.mareczek100.domain.CarToSellTempStorage;
import pl.mareczek100.infrastructure.database.entity.CarToSellTempStorageEntity;


public interface CarToSellTempStorageEntityMapper {
    CarToSellTempStorage mapFromEntity(CarToSellTempStorageEntity carToSellTempStorageEntity);
    CarToSellTempStorageEntity mapToEntity(CarToSellTempStorage carToSellTempStorage);
}