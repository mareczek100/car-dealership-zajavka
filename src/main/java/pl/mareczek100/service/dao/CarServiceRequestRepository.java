package pl.mareczek100.service.dao;

import pl.mareczek100.infrastructure.database.entity.CarServiceRequest;

import java.util.List;
import java.util.Optional;


public interface CarServiceRequestRepository {


    void insertCarServiceRequest(CarServiceRequest carServiceRequest);

    Optional<CarServiceRequest> findCarServiceRequestsByCarVin(String vin);

    List<CarServiceRequest> findAllCarServiceRequest();
}
