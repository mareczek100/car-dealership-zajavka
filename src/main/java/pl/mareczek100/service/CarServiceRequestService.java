package pl.mareczek100.service;

import lombok.Value;
import org.springframework.stereotype.Service;
import pl.mareczek100.domain.CarServiceRequest;
import pl.mareczek100.domain.inputTrafficData.data_storage.CarServiceRequestDataStorage;
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
        CarServiceRequest carServiceRequest = carServiceRequestDataStorage.createCarServiceRequest(email);
        carServiceRequestRepository.insertCarServiceRequest(carServiceRequest);
    }

    public void findCarServiceRequestHistory(String vin) {
        System.out.printf("Service history for car [%s]:", vin);
        Set<CarServiceRequest> carServiceRequest = carToServiceService.findCarToService(vin).getCarServiceRequest();
        carServiceRequest.forEach(System.out::println);
    }
    public CarServiceRequest findCarServiceRequest(String vin) {
        return carServiceRequestRepository.findCarServiceRequestsByCarVin(vin)
                .orElseThrow(() -> new RuntimeException(
                        "Service request for car [%s] no exist".formatted(vin)));
    }

    public void findAllCarServiceRequest(){
        System.out.println("Service history for all cars: ");
        List<CarServiceRequest> allCarServiceRequest = carServiceRequestRepository.findAllCarServiceRequest();
        if (allCarServiceRequest.isEmpty()){
            throw  new RuntimeException("No car service request's at all!");
        }
        allCarServiceRequest.forEach(System.out::println);
    }
}