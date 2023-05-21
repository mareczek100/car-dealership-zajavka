package pl.mareczek100.infrastructure.data_storage;

import lombok.Value;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.database.entity.MechanicEntity;

import java.util.List;

@Value
@Repository
public class MechanicDataStorage {

  TrafficData trafficData;

    public List<MechanicEntity> createMechanic() {
        return trafficData.getMechanicList().stream()
                .map(string -> string.split(";"))
                .map(arr -> MechanicEntity.builder()
                        .name(arr[0])
                        .surname(arr[1])
                        .pesel(arr[2])
                        .build())
                .toList();
    }
}
