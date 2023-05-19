package pl.mareczek100.service;

import lombok.Value;
import org.springframework.stereotype.Service;
import pl.mareczek100.infrastructure.data_storage.CarServiceHandlingDataStorage;
import pl.mareczek100.infrastructure.database.entity.CarServiceHandling;
import pl.mareczek100.infrastructure.database.entity.CarServiceParts;
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
        List<CarServiceParts> carServiceParts = carServicePartsService.createCarServiceParts();
        for (int i = 0; i < carServiceHandling.size(); i++) {
            carServiceHandlingRepository.carServiceHandlingInit(carServiceHandling.get(i));
            carServiceParts.get(i).setCarServiceRequest(carServiceHandling.get(i).getCarServiceRequest());
        }
        carServiceParts.forEach(carServicePartsService::carServiceInit);
    }


}
