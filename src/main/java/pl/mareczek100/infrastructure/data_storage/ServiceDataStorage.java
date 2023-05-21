package pl.mareczek100.infrastructure.data_storage;

import lombok.Value;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.database.entity.ServiceEntity;

import java.math.BigDecimal;
import java.util.List;
@Value
@Repository
public class ServiceDataStorage {

    TrafficData trafficData;

    public List<ServiceEntity> createService() {

        return trafficData.getServiceList().stream()
                .map(string -> string.split(";"))
                .map(arr -> ServiceEntity.builder()
                                .serviceCode(arr[0])
                                .description(arr[1])
                        .price(new BigDecimal(arr[2]))
                        .build())
                .toList();
    }

}
