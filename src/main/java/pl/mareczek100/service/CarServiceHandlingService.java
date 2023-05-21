package pl.mareczek100.service;

import lombok.Value;
import org.springframework.stereotype.Service;
import pl.mareczek100.infrastructure.data_storage.CarServiceHandlingDataStorage;
import pl.mareczek100.infrastructure.database.entity.CarServiceHandlingEntity;
import pl.mareczek100.infrastructure.database.entity.CarServicePartsEntity;
import pl.mareczek100.service.dao.CarServiceHandlingRepository;

import java.util.List;

@Value
@Service
public class CarServiceHandlingService {
    CarServiceHandlingDataStorage carServiceHandlingDataStorage;
    CarServiceHandlingRepository carServiceHandlingRepository;
    CarServicePartsService carServicePartsService;

    public void carServiceHandlingInit() {
        List<CarServiceHandlingEntity> carServiceHandlingEntity = carServiceHandlingDataStorage.createCarServiceHandling();
        List<CarServicePartsEntity> carServicePartEntities = carServicePartsService.createCarServiceParts();
        for (int i = 0; i < carServiceHandlingEntity.size(); i++) {
            carServiceHandlingRepository.carServiceHandlingInit(carServiceHandlingEntity.get(i));
            carServicePartEntities.get(i).setCarServiceRequestEntity(carServiceHandlingEntity.get(i).getCarServiceRequestEntity());
        }
        carServicePartEntities.forEach(carServicePartsService::carServiceInit);
    }


}
