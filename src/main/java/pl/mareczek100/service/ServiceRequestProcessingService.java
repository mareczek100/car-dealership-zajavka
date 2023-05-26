package pl.mareczek100.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mareczek100.domain.CarServiceHandling;
import pl.mareczek100.domain.CarServiceParts;
import pl.mareczek100.domain.inputTrafficData.data_storage.CarServiceProcessDataStorage;
import pl.mareczek100.service.dao.ServiceRequestProcessingRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ServiceRequestProcessingService {

    private final ServiceRequestProcessingRepository serviceRequestProcessingRepository;
    private final CarServiceProcessDataStorage carServiceProcessDataStorage;

    @Transactional
    public void serviceRequestProcess() {
        List<CarServiceHandling> carServiceHandling = carServiceProcessDataStorage.createCarServiceHandling();
        List<CarServiceParts> carServiceParts = carServiceProcessDataStorage.createCarServiceParts();

        for (int i = 0; i < carServiceHandling.size(); i++) {
            serviceRequestProcessingRepository.serviceRequestProcess(
                    carServiceHandling.get(i), carServiceParts.get(i));
        }
    }
}
