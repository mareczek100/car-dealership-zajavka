package pl.mareczek100.service.dao;

import pl.mareczek100.infrastructure.database.entity.CarToService;
import pl.mareczek100.infrastructure.database.entity.CarToServiceHistory;

import java.util.List;
import java.util.Optional;


public interface CarToServiceRepository {


    void carToServiceInsert(CarToService carToService);

    List<CarToService> findAllCarsToService();


    Optional<CarToService> findCarToService(String vin);

    CarToServiceHistory findCarHistoryByVin(String vin);
}