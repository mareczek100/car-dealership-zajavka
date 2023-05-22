package pl.mareczek100.service;

import lombok.Value;
import org.springframework.stereotype.Service;
import pl.mareczek100.domain.CarServiceHandling;
import pl.mareczek100.domain.CarServiceParts;
import pl.mareczek100.domain.inputTrafficData.data_storage.CarServiceHandlingDataStorage;
import pl.mareczek100.service.dao.CarServiceHandlingRepository;

import java.util.List;

@Value
@Service
public class CarServiceHandlingService {
    CarServiceHandlingDataStorage carServiceHandlingDataStorage;
    CarServiceHandlingRepository carServiceHandlingRepository;
    CarServicePartsService carServicePartsService;

    public void carServiceHandlingInit() {
        List<CarServiceHandling> carServiceHandling = carServiceHandlingDataStorage.createCarServiceHandling();
        List<CarServiceParts> carServicePart = carServicePartsService.createCarServiceParts();
        for (int i = 0; i < carServiceHandling.size(); i++) {
            carServiceHandlingRepository.carServiceHandlingInit(carServiceHandling.get(i));
            carServicePart.get(i).setCarServiceRequest(carServiceHandling.get(i).getCarServiceRequest());
        }
        carServicePart.forEach(carServicePartsService::carServiceInit);
    }


}
