package pl.mareczek100.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mareczek100.domain.CarToSell;
import pl.mareczek100.domain.CarToService;
import pl.mareczek100.service.dao.CarToServiceRepository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CarToServiceService {

    private final CarToServiceRepository carToServiceRepository;
    private final CarToSellService carToSellService;
    @Transactional
    public CarToService createCarToService(String vin) {
        CarToService existingCarToService = findCarToService(vin);
        CarToSell existingCarToSell = carToSellService.findCarToSell(vin);
        if (Objects.nonNull(existingCarToService)) {
            return existingCarToService;
        } else if (Objects.nonNull(existingCarToSell)) {
//            return carToServiceDataStorage.createCarToServiceFromDealer(existingCarToSell);
//        }else {
//            return carToServiceDataStorage.createCarToServiceFromOutside(vin);
        }
        return null;
    }
    @Transactional
    public CarToService carToServiceInit(String vin) {
        return carToServiceRepository.carToServiceInsert(createCarToService(vin));
    }
    @Transactional
    public CarToService findCarToService(String vin) {
        return carToServiceRepository.findCarToService(vin).orElse(null);
    }
    @Transactional
    public List<CarToService> findAllCarsToService() {
        List<CarToService> allCarsToService = carToServiceRepository.findAllCarsToService();
        if (allCarsToService.isEmpty()) {
            System.out.println("Sorry, no cars in our service database!");
            return Collections.emptyList();
        }
        return allCarsToService;
    }

}