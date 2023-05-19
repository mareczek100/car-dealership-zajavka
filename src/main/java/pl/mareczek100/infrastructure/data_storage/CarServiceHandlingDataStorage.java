package pl.mareczek100.infrastructure.data_storage;

import lombok.Value;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.database.entity.CarServiceHandling;
import pl.mareczek100.infrastructure.database.entity.CarServiceRequest;
import pl.mareczek100.service.CarServiceRequestService;
import pl.mareczek100.service.MechanicService;
import pl.mareczek100.service.ServiceService;

import java.time.OffsetDateTime;
import java.util.List;

@Value
@Repository
public class CarServiceHandlingDataStorage {
    private final static String FINISHED = "FINISHED";
    TrafficData trafficData;
    ServiceService serviceService;
    MechanicService mechanicService;
    CarServiceRequestService carServiceRequestService;


    public List<CarServiceHandling> createCarServiceHandling() {

        return trafficData.getCarServiceHandlingList().stream()
                .map(string -> string.split(";"))
                .map(arr -> CarServiceHandling.builder()
                        .hours(Short.parseShort(arr[5]))
                        .comment(arr[6])
                        .carServiceRequest(getCarServiceRequest(arr))
                        .mechanic(mechanicService.findMechanic(arr[0]))
                        .service(serviceService.findService(arr[4]))
                        .build())
                .toList();
    }

    private CarServiceRequest getCarServiceRequest(String[] arr) {
        CarServiceRequest carServiceRequest = carServiceRequestService.findCarServiceRequest(arr[1]);
        if (arr[7].equalsIgnoreCase(FINISHED)) {
            carServiceRequest.setCompletedDateTime(OffsetDateTime.now());
            return carServiceRequest;
        }
        return carServiceRequest;
    }


}
