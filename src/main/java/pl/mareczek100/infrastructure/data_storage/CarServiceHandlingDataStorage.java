package pl.mareczek100.infrastructure.data_storage;

import lombok.Value;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.database.entity.CarServiceHandlingEntity;
import pl.mareczek100.infrastructure.database.entity.CarServiceRequestEntity;
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


    public List<CarServiceHandlingEntity> createCarServiceHandling() {

        return trafficData.getCarServiceHandlingList().stream()
                .map(string -> string.split(";"))
                .map(arr -> CarServiceHandlingEntity.builder()
                        .hours(Short.parseShort(arr[5]))
                        .comment(arr[6])
                        .carServiceRequestEntity(getCarServiceRequest(arr))
                        .mechanicEntity(mechanicService.findMechanic(arr[0]))
                        .serviceEntity(serviceService.findService(arr[4]))
                        .build())
                .toList();
    }

    private CarServiceRequestEntity getCarServiceRequest(String[] arr) {
        CarServiceRequestEntity carServiceRequestEntity = carServiceRequestService.findCarServiceRequest(arr[1]);
        if (arr[7].equalsIgnoreCase(FINISHED)) {
            carServiceRequestEntity.setCompletedDateTime(OffsetDateTime.now());
            return carServiceRequestEntity;
        }
        return carServiceRequestEntity;
    }


}
