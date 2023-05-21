package pl.mareczek100.service.dao;

import pl.mareczek100.infrastructure.database.entity.CarServiceRequestEntity;

import java.util.List;
import java.util.Optional;


public interface CarServiceRequestRepository {


    void insertCarServiceRequest(CarServiceRequestEntity carServiceRequestEntity);

    Optional<CarServiceRequestEntity> findCarServiceRequestsByCarVin(String vin);

    List<CarServiceRequestEntity> findAllCarServiceRequest();
}
