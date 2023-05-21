package pl.mareczek100.infrastructure.data_storage;

import lombok.Value;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.database.entity.CarServicePartsEntity;
import pl.mareczek100.infrastructure.database.entity.PartEntity;
import pl.mareczek100.service.PartService;

import java.util.List;

@Value
@Repository
public class CarServicePartsDataStorage {

    TrafficData trafficData;
    PartService partService;
    public List<CarServicePartsEntity> createCarServiceParts() {

        return trafficData.getCarServiceHandlingList().stream()
                .map(string -> string.split(";"))
                .map(arr -> CarServicePartsEntity.builder()
                        .quantity(getQuantity(arr[3]))
                        .partEntity(getPart(arr[2]))
                        .build())
                .toList();
    }
    private static Short getQuantity(String quantity) {
        if (quantity.isBlank()) {
            return null;
        }
        return Short.valueOf(quantity);
    }
    private PartEntity getPart(String partNumber) {
        if (partNumber.isBlank()) {
            return null;
        }
        return partService.findPart(partNumber);
    }
}
