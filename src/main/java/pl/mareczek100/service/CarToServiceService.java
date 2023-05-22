package pl.mareczek100.service;

import lombok.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mareczek100.domain.*;
import pl.mareczek100.domain.inputTrafficData.data_storage.CarToServiceDataStorage;
import pl.mareczek100.service.dao.CarToServiceRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Value
@Service
public class CarToServiceService {

    CarToServiceDataStorage carToServiceDataStorage;
    CarToServiceRepository carToServiceRepository;
    CarToSellService carToSellService;
    @Transactional
    public CarToService createCarToService(String vin) {
        CarToService existingCarToService = findCarToService(vin);
        CarToSell existingCarToSell = carToSellService.findCarToSell(vin);
        if (Objects.nonNull(existingCarToService)) {
            return existingCarToService;
        } else if (Objects.nonNull(existingCarToSell)) {
            return carToServiceDataStorage.createCarToServiceFromDealer(existingCarToSell);
        }else {
            return carToServiceDataStorage.createCarToServiceFromOutside(vin);
        }
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
    @Transactional
    public void printCarHistory(String vin) {
        CarToService carHistoryByVin = carToServiceRepository.findCarHistoryByVin(vin);
        System.out.printf("###CAR HISTORY FOR VIN: [%s]%n", vin);
        printServiceRequest(carHistoryByVin);
    }

    private void printServiceRequest(CarToService serviceRequest) {
        System.out.printf("###SERVICE REQUEST: [%s]%n", serviceRequest);
        serviceRequest.getCarServiceRequests()
                .stream()
                .map(CarServiceRequest::getCarServiceHandling)
                .flatMap(Collection::stream)
                .map(CarServiceHandling::getService)
                .forEach(service -> System.out.printf("###SERVICE: [%s]%n", service));

        serviceRequest.getCarServiceRequests()
                .stream()
                .map(CarServiceRequest::getCarServicePart)
                .flatMap(Collection::stream)
                .map(CarServiceParts::getPart)
                .forEach(part -> System.out.printf("###PART: [%s]%n", part));
    }


}