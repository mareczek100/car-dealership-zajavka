package pl.mareczek100.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mareczek100.domain.CarToSell;
import pl.mareczek100.domain.CarToSellTempStorage;
import pl.mareczek100.service.dao.CarToSellRepository;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class CarToSellService {

    private final CarToSellRepository carToSellRepository;
    private final CarToSellTempStorageService carToSellTempStorageService;

    @Transactional
    public CarToSell findCarToSell(String vin) {
        return carToSellRepository.findCarToSell(vin).orElse(null);
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