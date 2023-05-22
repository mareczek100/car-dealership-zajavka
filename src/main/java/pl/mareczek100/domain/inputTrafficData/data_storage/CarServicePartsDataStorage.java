package pl.mareczek100.domain.inputTrafficData.data_storage;

import lombok.Value;
import org.springframework.stereotype.Repository;
import pl.mareczek100.domain.CarServiceParts;
import pl.mareczek100.domain.Part;
import pl.mareczek100.service.PartService;

import java.util.List;

@Value
@Repository
public class CarServicePartsDataStorage {

    TrafficData trafficData;
    PartService partService;
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
}
