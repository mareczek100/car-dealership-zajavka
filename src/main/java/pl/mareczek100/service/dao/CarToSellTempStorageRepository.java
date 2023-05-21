package pl.mareczek100.service.dao;

import pl.mareczek100.infrastructure.database.entity.CarToSellTempStorageEntity;

import java.util.List;
import java.util.Optional;


public interface CarToSellTempStorageRepository {

    void carToSellTempStorageInit(CarToSellTempStorageEntity carToSellTempStorageEntity);
    Optional<CarToSellTempStorageEntity> findCarToSellTempStorage(String vin);
    List<CarToSellTempStorageEntity> findAllCarsToSellTempStorage();
    void deleteCarToSellTempStorageByCarVin(String vin);
    void deleteCarToSellTempStorage(CarToSellTempStorageEntity carToSellTempStorageEntity);
}