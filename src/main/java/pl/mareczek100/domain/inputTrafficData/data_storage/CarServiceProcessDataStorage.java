package pl.mareczek100.domain.inputTrafficData.data_storage;

import lombok.Value;
import org.springframework.stereotype.Repository;
import pl.mareczek100.domain.CarServiceHandling;
import pl.mareczek100.domain.CarServiceParts;
import pl.mareczek100.domain.CarServiceRequest;
import pl.mareczek100.domain.Part;
import pl.mareczek100.service.CarServiceRequestService;
import pl.mareczek100.service.MechanicService;
import pl.mareczek100.service.PartService;
import pl.mareczek100.service.ServiceService;

import java.time.OffsetDateTime;
import java.util.List;

@Value
@Repository
public class CarServiceProcessDataStorage {
    private final static String FINISHED = "FINISHED";
    TrafficData trafficData;
    PartService partService;
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
    public List<CarServiceParts> createCarServiceParts() {

        return trafficData.getCarServiceHandlingList().stream()
                .map(string -> string.split(";"))
                .map(arr -> CarServiceParts.builder()
                        .quantity(getQuantity(arr[3]))
                        .part(getPart(arr[2]))
                        .build())
                .toList();
    }
    private static Short getQuantity(String quantity) {
        if (quantity.isBlank()) {
            return null;
        }
        return Short.valueOf(quantity);
    }
    private Part getPart(String partNumber) {
        if (partNumber.isBlank()) {
            return null;
        }
        return partService.findPart(partNumber);
    }

    public List<String[]> createCarServiceProcessingInputData() {
        return trafficData.getCarServiceHandlingList().stream()
                .map(string -> string.split(";"))
                .toList();
    }
    private CarServiceRequest getCarServiceRequest(String[] arr) {
        CarServiceRequest carServiceRequest = carServiceRequestService.findCarServiceRequest(arr[1]);
        if (arr[7].equalsIgnoreCase(FINISHED)) {
            carServiceRequest = carServiceRequest.withCompletedDateTime(OffsetDateTime.now());
            return carServiceRequest;
        }
        return carServiceRequest;
    }
}