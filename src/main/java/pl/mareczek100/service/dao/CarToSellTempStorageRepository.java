package pl.mareczek100.service.dao;

import pl.mareczek100.domain.CarToSellTempStorage;

import java.util.List;
import java.util.Optional;


public interface CarToSellTempStorageRepository {

    Optional<CarToSellTempStorage> findCarToSellTempStorage(String vin);
    List<CarToSellTempStorage> findAllCarsToSellTempStorage();
    void deleteCarToSellTempStorage(CarToSellTempStorage carToSellTempStorage);
}