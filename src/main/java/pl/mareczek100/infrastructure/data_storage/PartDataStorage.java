package pl.mareczek100.infrastructure.data_storage;

import lombok.Value;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.database.entity.Part;

import java.math.BigDecimal;
import java.util.List;

@Value
@Repository
public class PartDataStorage {
    TrafficData trafficData;

    public List<Part> createPart() {

        return trafficData.getPartList().stream()
                .map(string -> string.split(";"))
                .map(arr -> Part.builder()
                                .serialNumber(arr[0])
                                .description(arr[1])
                        .price(new BigDecimal(arr[2]))
                        .build())
                .toList();
    }
}
