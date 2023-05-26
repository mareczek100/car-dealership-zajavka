package pl.mareczek100.service.dao;

import pl.mareczek100.domain.CarToService;

import java.util.List;
import java.util.Optional;


public interface CarToServiceRepository {

    CarToService carToServiceInsert(CarToService carToService);

    List<CarToService> findAllCarsToService();

    Optional<CarToService> findCarToService(String vin);

}