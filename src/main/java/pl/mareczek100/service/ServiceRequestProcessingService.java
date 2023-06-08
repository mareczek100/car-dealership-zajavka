package pl.mareczek100.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mareczek100.service.dao.ServiceRequestProcessingRepository;

@Service
@AllArgsConstructor
public class ServiceRequestProcessingService {

    private final ServiceRequestProcessingRepository serviceRequestProcessingRepository;

    @Transactional
    public void serviceRequestProcess() {
//        List<CarServiceHandling> carServiceHandling = carServiceProcessDataStorage.createCarServiceHandling();
//        List<CarServiceParts> carServiceParts = carServiceProcessDataStorage.createCarServiceParts();
//
//        for (int i = 0; i < carServiceHandling.size(); i++) {
//            serviceRequestProcessingRepository.serviceRequestProcess(
//                    carServiceHandling.get(i), carServiceParts.get(i));
//        }
    }
}
