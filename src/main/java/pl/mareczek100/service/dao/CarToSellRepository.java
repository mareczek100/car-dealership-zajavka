package pl.mareczek100.service.dao;

import pl.mareczek100.domain.CarToSell;

import java.util.List;
import java.util.Optional;


public interface CarToSellRepository {


    void carToSellInit(CarToSell carToSell);

    Optional<CarToSell> findCarToSell(String vin);

    List<CarToSell> findAllCarsToSell();

    void deleteCar(String vin);
}