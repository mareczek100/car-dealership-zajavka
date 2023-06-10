package pl.mareczek100.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mareczek100.domain.CarToSellTempStorage;
import pl.mareczek100.service.dao.CarToSellTempStorageRepository;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class CarToSellTempStorageService {

    private final CarToSellTempStorageRepository carToSellTempStorageRepository;

    @Transactional
    public CarToSellTempStorage findCarToSellTempStorage(String vin) {
        return carToSellTempStorageRepository.findCarToSellTempStorage(vin)
                .orElseThrow(() -> new RuntimeException(
                        "Car [%s] is no longer available! Maybe You'll be interested in other cars?\n".formatted(vin) +
                                "List of cars to buy: \n[%s]".formatted(findAllCarsToSellTempStorage())));
    }
    @Transactional
    public List<CarToSellTempStorage> findAllCarsToSellTempStorage() {
        List<CarToSellTempStorage> allCarsToSell = carToSellTempStorageRepository.findAllCarsToSellTempStorage();
        if (allCarsToSell.isEmpty()) {
            System.out.println("Sorry, no cars to buy at the moment available! Try again another time!");
            return Collections.emptyList();
        }
        return allCarsToSell;
    }
    @Transactional
    void deleteCarToSellTempStorage(String vin){
        CarToSellTempStorage carToSellTempStorage = findCarToSellTempStorage(vin);
        carToSellTempStorageRepository.deleteCarToSellTempStorage(carToSellTempStorage);
    }

}