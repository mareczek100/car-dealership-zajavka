package pl.mareczek100.service;

import lombok.Value;
import org.springframework.stereotype.Service;
import pl.mareczek100.infrastructure.data_storage.CarToSellTempStorageDataStorage;
import pl.mareczek100.infrastructure.database.entity.CarToSellTempStorage;
import pl.mareczek100.service.dao.CarToSellTempStorageRepository;

import java.util.Collections;
import java.util.List;

@Value
@Service
public class CarToSellTempStorageService {
    CarToSellTempStorageDataStorage carToSellTempStorageDataStorage;
    CarToSellTempStorageRepository carToSellTempStorageRepository;

    public void carToSellTempStorageInit() {
        carToSellTempStorageDataStorage.createCarToSellTempStorage()
                .forEach(carToSellTempStorageRepository::carToSellTempStorageInit);
    }

    public CarToSellTempStorage findCarToSellTempStorage(String vin) {
        return carToSellTempStorageRepository.findCarToSellTempStorage(vin)
                .orElseThrow(() -> new RuntimeException(
                        "Car [%s] is no longer available! Maybe You'll be interested in other cars?\n".formatted(vin) +
                                "List of cars to buy: \n[%s]".formatted(findAllCarsToSellTempStorage())));
    }

    public List<CarToSellTempStorage> findAllCarsToSellTempStorage() {
        List<CarToSellTempStorage> allCarsToSell = carToSellTempStorageRepository.findAllCarsToSellTempStorage();
        if (allCarsToSell.isEmpty()) {
            System.out.println("Sorry, no cars to buy at the moment available! Try again another time!");
            return Collections.emptyList();
        }
        return allCarsToSell;
    }

    public void deleteCarToSellTempStorageByVin(String vin) {
        findCarToSellTempStorage(vin);
        carToSellTempStorageRepository.deleteCarToSellTempStorageByCarVin(vin);
    }

    void deleteCarToSellTempStorage(String vin){
        CarToSellTempStorage carToSellTempStorage = findCarToSellTempStorage(vin);
        carToSellTempStorageRepository.deleteCarToSellTempStorage(carToSellTempStorage);
    }

}