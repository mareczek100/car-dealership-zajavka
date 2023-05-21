package pl.mareczek100.service;

import lombok.Value;
import org.springframework.stereotype.Service;
import pl.mareczek100.infrastructure.data_storage.CarToSellDataStorage;
import pl.mareczek100.infrastructure.database.entity.CarToSellEntity;
import pl.mareczek100.infrastructure.database.entity.CarToSellTempStorageEntity;
import pl.mareczek100.service.dao.CarToSellRepository;

import java.util.Collections;
import java.util.List;

@Value
@Service
public class CarToSellService {
    CarToSellDataStorage carToSellDataStorage;
    CarToSellRepository carToSellRepository;
    CarToSellTempStorageService carToSellTempStorageService;


    public void carToSellInit() {
        carToSellDataStorage.createCarToSell()
                .forEach(carToSellRepository::carToSellInit);
        carToSellTempStorageService.carToSellTempStorageInit();
    }

    public CarToSellEntity findCarToSell(String vin) {
        return carToSellRepository.findCarToSell(vin).orElse(null);
    }

    public List<CarToSellEntity> findAllCarsToSell() {
        List<CarToSellEntity> allCarsToSell = carToSellRepository.findAllCarsToSell();
        if (allCarsToSell.isEmpty()) {
            System.out.println("Sorry, no cars to buy at the moment available! Try again another time!");
            return Collections.emptyList();
        }
        return allCarsToSell;
    }

    public CarToSellTempStorageEntity findAvailableCarToSell(String vin) {
        return carToSellTempStorageService.findCarToSellTempStorage(vin);
    }

    public List<CarToSellTempStorageEntity> findAllAvailableCarsToSell() {
        return carToSellTempStorageService.findAllCarsToSellTempStorage();
    }

    public void deleteFromAvailableCar(String vin) {
        carToSellTempStorageService.deleteCarToSellTempStorage(vin);
    }
    public void deleteCar(String vin) {
        carToSellRepository.deleteCar(vin);
    }
}