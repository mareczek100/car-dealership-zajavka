package pl.mareczek100.service;

import lombok.Value;
import org.springframework.stereotype.Service;
import pl.mareczek100.domain.CarToSell;
import pl.mareczek100.domain.CarToSellTempStorage;
import pl.mareczek100.service.dao.CarToSellRepository;

import java.util.Collections;
import java.util.List;

@Value
@Service
public class CarToSellService {
    CarToSellRepository carToSellRepository;
    CarToSellTempStorageService carToSellTempStorageService;


    public void carToSellInit() {
        carToSellTempStorageService.carToSellTempStorageInit();
    }

    public CarToSell findCarToSell(String vin) {
        return carToSellRepository.findCarToSell(vin)
                .orElseThrow(() ->
                        new RuntimeException("Car [%s] is no longer available!".formatted(vin)));
    }

    public List<CarToSell> findAllCarsToSell() {
        List<CarToSell> allCarsToSell = carToSellRepository.findAllCarsToSell();
        if (allCarsToSell.isEmpty()) {
            System.out.println("Sorry, no cars to buy at the moment available! Try again another time!");
            return Collections.emptyList();
        }
        return allCarsToSell;
    }

    public CarToSellTempStorage findAvailableCarToSell(String vin) {
        return carToSellTempStorageService.findCarToSellTempStorage(vin);
    }

    public List<CarToSellTempStorage> findAllAvailableCarsToSell() {
        return carToSellTempStorageService.findAllCarsToSellTempStorage();
    }

    public void deleteFromAvailableCar(String vin) {
        carToSellTempStorageService.deleteCarToSellTempStorage(vin);
    }
    public void deleteCar(String vin) {
        carToSellRepository.deleteCar(vin);
    }
}