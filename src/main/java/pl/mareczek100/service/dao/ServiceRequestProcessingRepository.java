package pl.mareczek100.service.dao;

import org.springframework.stereotype.Repository;
import pl.mareczek100.domain.CarServiceHandling;
import pl.mareczek100.domain.CarServiceParts;
import pl.mareczek100.domain.CarServiceRequest;

public interface ServiceRequestProcessingRepository {
    void serviceRequestProcess(
            CarServiceRequest carServiceRequest,
            CarServiceHandling carServiceHandling,
            CarServiceParts carServiceParts);
}
