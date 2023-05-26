package pl.mareczek100.service.dao;

import pl.mareczek100.domain.CarServiceHandling;
import pl.mareczek100.domain.CarServiceParts;

public interface ServiceRequestProcessingRepository {
    void serviceRequestProcess(CarServiceHandling carServiceHandling, CarServiceParts carServiceParts);
}
