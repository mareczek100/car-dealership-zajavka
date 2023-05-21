package pl.mareczek100.infrastructure.data_storage;

import lombok.Value;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.database.entity.CarToSellEntity;
import pl.mareczek100.infrastructure.database.entity.CarToServiceEntity;

@Value
@Repository
public class CarToServiceDataStorage {
    TrafficData trafficData;

    public CarToServiceEntity createCarToServiceFromDealer(CarToSellEntity carFromDealer) {

        return CarToServiceEntity.builder()
                        .vin(carFromDealer.getVin())
                        .brand(carFromDealer.getBrand())
                        .model(carFromDealer.getModel())
                        .year(carFromDealer.getYear())
                        .build();
    }

    public CarToServiceEntity createCarToServiceFromOutside(String vin) {

        return trafficData.getCarOuterServiceRequestList().stream()
                .filter(string -> string.contains(vin))
                .map(string -> string.split(";"))
                .map(arr -> CarToServiceEntity.builder()
                        .vin(arr[1])
                        .brand(arr[2])
                        .model(arr[3])
                        .year(Short.valueOf(arr[4]))
                        .build()).toList().get(0);
    }
}
