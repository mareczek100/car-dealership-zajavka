package pl.mareczek100.service.dao;

import pl.mareczek100.domain.CarHistory;
import pl.mareczek100.infrastructure.database.entity.CarToServiceEntity;

import java.util.List;
import java.util.Optional;


public interface CarToServiceRepository {


    void carToServiceInsert(CarToServiceEntity carToServiceEntity);

    List<CarToServiceEntity> findAllCarsToService();


    Optional<CarToServiceEntity> findCarToService(String vin);

    CarHistory findCarHistoryByVin(String vin);
}