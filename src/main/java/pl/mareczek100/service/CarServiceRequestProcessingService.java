package pl.mareczek100.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mareczek100.domain.CarServiceHandling;
import pl.mareczek100.domain.CarServiceParts;
import pl.mareczek100.domain.CarServiceRequest;
import pl.mareczek100.service.dao.ServiceRequestProcessingRepository;

@Service
@AllArgsConstructor
public class CarServiceRequestProcessingService {

    private final ServiceRequestProcessingRepository serviceRequestProcessingRepository;

    @Transactional
    public void insertServiceRequestProcess(CarServiceRequest serviceRequest,
                                            CarServiceHandling serviceHandling,
                                            CarServiceParts serviceParts) {

        serviceRequestProcessingRepository.serviceRequestProcess(serviceRequest, serviceHandling, serviceParts);
    }
}