package pl.mareczek100.service;

import lombok.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public void carToSellInit() {
        carToSellTempStorageService.carToSellTempStorageInit();
    }
    @Transactional
    public CarToSell findCarToSell(String vin) {
        return carToSellRepository.findCarToSell(vin)
                .orElseThrow(() ->
                        new RuntimeException("Car [%s] is no longer available!".formatted(vin)));
    }
    @Transactional
    public List<CarToSell> findAllCarsToSell() {
        List<CarToSell> allCarsToSell = carToSellRepository.findAllCarsToSell();
        if (allCarsToSell.isEmpty()) {
            System.out.println("Sorry, no cars to buy at the moment available! Try again another time!");
            return Collections.emptyList();
        }
        return allCarsToSell;
    }
    @Transactional
    public CarToSellTempStorage findAvailableCarToSell(String vin) {
        return carToSellTempStorageService.findCarToSellTempStorage(vin);
    }
    @Transactional
    public List<CarToSellTempStorage> findAllAvailableCarsToSell() {
        return carToSellTempStorageService.findAllCarsToSellTempStorage();
    }
    @Transactional
    public void deleteFromAvailableCar(String vin) {
        carToSellTempStorageService.deleteCarToSellTempStorage(vin);}
    @Transactional
    public void deleteCar(String vin) {
        carToSellRepository.deleteCar(vin);
    }
}