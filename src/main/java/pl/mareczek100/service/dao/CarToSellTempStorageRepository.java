package pl.mareczek100.service.dao;

import pl.mareczek100.infrastructure.database.entity.CarToSellTempStorage;

import java.util.List;
import java.util.Optional;


public interface CarToSellTempStorageRepository {

    void carToSellTempStorageInit(CarToSellTempStorage carToSellTempStorage);
    Optional<CarToSellTempStorage> findCarToSellTempStorage(String vin);
    List<CarToSellTempStorage> findAllCarsToSellTempStorage();
    void deleteCarToSellTempStorageByCarVin(String vin);
    void deleteCarToSellTempStorage(CarToSellTempStorage carToSellTempStorage);
}