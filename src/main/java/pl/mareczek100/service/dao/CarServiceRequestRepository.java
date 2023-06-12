package pl.mareczek100.service.dao;

import pl.mareczek100.domain.CarServiceRequest;

import java.util.List;
import java.util.Optional;


public interface CarServiceRequestRepository {

    CarServiceRequest insertCarServiceRequest(CarServiceRequest carServiceRequest);

    List<CarServiceRequest> findCarServiceRequestsByCarVin(String vin);

    List<CarServiceRequest> findAllCarServiceRequest();

    Optional<CarServiceRequest> findCarServiceRequestByCarServiceRequestNumber(String requestNumber);
}
