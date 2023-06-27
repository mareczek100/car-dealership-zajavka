package pl.mareczek100.service.dao;

import pl.mareczek100.domain.CarServiceHandling;
import pl.mareczek100.domain.CarServiceParts;
import pl.mareczek100.domain.CarServiceRequest;

public interface ServiceRequestProcessingRepository {
    CarServiceRequest serviceRequestProcess(CarServiceHandling carServiceHandling, CarServiceParts carServiceParts);
}
