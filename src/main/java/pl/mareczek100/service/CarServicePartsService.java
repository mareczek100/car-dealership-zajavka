package pl.mareczek100.service;

import lombok.Value;
import org.springframework.stereotype.Service;
import pl.mareczek100.domain.CarServiceParts;
import pl.mareczek100.domain.inputTrafficData.data_storage.CarServicePartsDataStorage;
import pl.mareczek100.service.dao.CarServicePartsRepository;

import java.util.List;

@Value
@Service
public class CarServicePartsService {

    CarServicePartsRepository carServicePartsRepository;
    CarServicePartsDataStorage carServicePartsDataStorage;

    public List<CarServiceParts> createCarServiceParts(){
        return carServicePartsDataStorage.createCarServiceParts();
    }

    public void carServiceInit(CarServiceParts carServiceParts){
       carServicePartsRepository.carServicePartsInit(carServiceParts);
    }

}
