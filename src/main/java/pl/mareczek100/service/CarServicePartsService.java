package pl.mareczek100.service;

import lombok.Value;
import org.springframework.stereotype.Service;
import pl.mareczek100.infrastructure.data_storage.CarServicePartsDataStorage;
import pl.mareczek100.infrastructure.database.entity.CarServicePartsEntity;
import pl.mareczek100.service.dao.CarServicePartsRepository;

import java.util.List;

@Value
@Service
public class CarServicePartsService {

    CarServicePartsRepository carServicePartsRepository;
    CarServicePartsDataStorage carServicePartsDataStorage;

    public List<CarServicePartsEntity> createCarServiceParts(){
        return carServicePartsDataStorage.createCarServiceParts();
    }

    public void carServiceInit(CarServicePartsEntity carServicePartsEntity){
       carServicePartsRepository.carServicePartsInit(carServicePartsEntity);
    }

}
