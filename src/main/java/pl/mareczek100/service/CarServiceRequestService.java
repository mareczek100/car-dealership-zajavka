package pl.mareczek100.service;

import lombok.Value;
import org.springframework.stereotype.Service;
import pl.mareczek100.infrastructure.data_storage.CarServiceRequestDataStorage;
import pl.mareczek100.infrastructure.database.entity.CarServiceRequestEntity;
import pl.mareczek100.service.dao.CarServiceRequestRepository;

import java.util.List;
import java.util.Set;

@Value
@Service
public class CarServiceRequestService {

    CarServiceRequestDataStorage carServiceRequestDataStorage;
    CarServiceRequestRepository carServiceRequestRepository;
    CarToServiceService carToServiceService;

    public void createCarServiceRequest(String email) {
        CarServiceRequestEntity carServiceRequestEntity = carServiceRequestDataStorage.createCarServiceRequest(email);
        carServiceRequestRepository.insertCarServiceRequest(carServiceRequestEntity);
    }

    public void findCarServiceRequestHistory(String vin) {
        System.out.printf("Service history for car [%s]:", vin);
        Set<CarServiceRequestEntity> carServiceRequestEntities = carToServiceService.findCarToService(vin).getCarServiceRequestEntities();
        carServiceRequestEntities.forEach(System.out::println);
    }
    public CarServiceRequestEntity findCarServiceRequest(String vin) {
        return carServiceRequestRepository.findCarServiceRequestsByCarVin(vin)
                .orElseThrow(() -> new RuntimeException(
                        "Service request for car [%s] no exist".formatted(vin)));
    }

    public void findAllCarServiceRequest(){
        System.out.println("Service history for all cars: ");
        List<CarServiceRequestEntity> allCarServiceRequestEntities = carServiceRequestRepository.findAllCarServiceRequest();
        if (allCarServiceRequestEntities.isEmpty()){
            throw  new RuntimeException("No car service request's at all!");
        }
        allCarServiceRequestEntities.forEach(System.out::println);
    }
}