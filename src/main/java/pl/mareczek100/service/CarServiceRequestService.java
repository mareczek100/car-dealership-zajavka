package pl.mareczek100.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mareczek100.domain.CarServiceHandling;
import pl.mareczek100.domain.CarServiceParts;
import pl.mareczek100.domain.CarServiceRequest;
import pl.mareczek100.service.dao.CarServiceRequestRepository;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CarServiceRequestService {

    private final CarServiceRequestRepository carServiceRequestRepository;

    @Transactional
    public void createCarServiceRequestInner() {
//        carServiceRequestDataStorage.createInnerCarServiceRequest().forEach(
//                carServiceRequestRepository::insertCarServiceRequest);
    }
    @Transactional
    public void createCarServiceRequestOuter() {
//        carServiceRequestDataStorage.createOuterCarServiceRequest().forEach(
//                carServiceRequestRepository::insertCarServiceRequest);
    }

    @Transactional
    public CarServiceRequest findCarServiceRequest(String vin) {
        return carServiceRequestRepository.findCarServiceRequestsByCarVin(vin)
                .orElseThrow(() -> new RuntimeException(
                        "Service request for car [%s] no exist".formatted(vin)));
    }

    @Transactional
    public List<CarServiceRequest> findAllCarServiceRequest() {
        System.out.println("Service history for all cars: ");
        List<CarServiceRequest> allCarServiceRequest = carServiceRequestRepository.findAllCarServiceRequest();
        if (allCarServiceRequest.isEmpty()) {
            throw new RuntimeException("No car service request's at all!");
        }
        allCarServiceRequest.forEach(System.out::println);
        return allCarServiceRequest;
    }

    @Transactional
    public void printCarHistory(String vin) {
        CarServiceRequest carServiceRequest = findCarServiceRequest(vin);
        System.out.printf("###CAR HISTORY FOR CAR: [%s]%n", carServiceRequest.getCarToService());
        System.out.printf("###CAR OWNER: [%s]%n", carServiceRequest.getCustomer());
        printServiceRequest(carServiceRequest);
    }

    private void printServiceRequest(CarServiceRequest carHistory) {
        System.out.printf("###SERVICE REQUEST: [%s]%n", carHistory);
        carHistory.getCarServiceHandling().stream()
                .map(CarServiceHandling::getService)
                .forEach(service -> System.out.printf("###SERVICE: [%s]%n", service));

        carHistory.getCarServicePart().stream()
                .map(CarServiceParts::getPart)
                .filter(Objects::nonNull)
                .forEach(part -> System.out.printf("###PART: [%s]%n", part));
    }
}