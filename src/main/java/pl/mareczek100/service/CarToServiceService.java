package pl.mareczek100.service;

import lombok.Value;
import org.springframework.stereotype.Service;
import pl.mareczek100.domain.CarHistory;
import pl.mareczek100.infrastructure.data_storage.CarToServiceDataStorage;
import pl.mareczek100.infrastructure.database.entity.CarToSellEntity;
import pl.mareczek100.infrastructure.database.entity.CarToServiceEntity;
import pl.mareczek100.service.dao.CarToServiceRepository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Value
@Service
public class CarToServiceService {

    CarToServiceDataStorage carToServiceDataStorage;
    CarToServiceRepository carToServiceRepository;
    CarToSellService carToSellService;

    public CarToServiceEntity createCarToService(String vin) {
        CarToServiceEntity existingCarToServiceEntity = findCarToService(vin);
        CarToSellEntity existingCarToSellEntity = carToSellService.findCarToSell(vin);
        if (Objects.nonNull(existingCarToServiceEntity)) {
            return existingCarToServiceEntity;
        } else if (Objects.nonNull(existingCarToSellEntity)) {
            return carToServiceDataStorage.createCarToServiceFromDealer(existingCarToSellEntity);
        }else {
            return carToServiceDataStorage.createCarToServiceFromOutside(vin);
        }
    }

    public CarToServiceEntity carToServiceInit(String vin) {
        carToServiceRepository.carToServiceInsert(createCarToService(vin));
        return findCarToService(vin);
    }

    public CarToServiceEntity findCarToService(String vin) {
        return carToServiceRepository.findCarToService(vin).orElse(null);
    }

    public List<CarToServiceEntity> findAllCarsToService() {
        List<CarToServiceEntity> allCarsToService = carToServiceRepository.findAllCarsToService();
        if (allCarsToService.isEmpty()) {
            System.out.println("Sorry, no cars in our service database!");
            return Collections.emptyList();
        }
        return allCarsToService;
    }

    public void printCarHistory(String vin) {
        CarHistory carHistoryByVin = carToServiceRepository.findCarHistoryByVin(vin);
        System.out.printf("###CAR HISTORY FOR VIN: [%s]%n", vin);
        carHistoryByVin.getServiceRequests().forEach(this::printServiceRequest);
    }

    private void printServiceRequest(CarHistory.ServiceRequest serviceRequest) {
        System.out.printf("###SERVICE REQUEST: [%s]%n", serviceRequest);
        serviceRequest.services().forEach(service -> System.out.printf("###SERVICE: [%s]%n", service));
        serviceRequest.parts().forEach(part -> System.out.printf("###PART: [%s]%n", part));
    }


}