package pl.mareczek100.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mareczek100.domain.CarToService;
import pl.mareczek100.service.dao.CarToServiceRepository;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class CarToServiceService {

    private final CarToServiceRepository carToServiceRepository;
    @Transactional
    public CarToService insertCarToService(CarToService carToService) {
        return carToServiceRepository.carToServiceInsert(carToService);
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