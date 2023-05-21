package pl.mareczek100.service.dao;

import pl.mareczek100.infrastructure.database.entity.CarToSellEntity;

import java.util.List;
import java.util.Optional;


public interface CarToSellRepository {


    void carToSellInit(CarToSellEntity carToSellEntity);

    Optional<CarToSellEntity> findCarToSell(String vin);

    List<CarToSellEntity> findAllCarsToSell();

    void deleteCar(String vin);
}