package pl.mareczek100.infrastructure.data_storage;

import lombok.Value;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.database.entity.CarToSell;
import pl.mareczek100.infrastructure.database.entity.CarToService;

@Value
@Repository
public class CarToServiceDataStorage {
    TrafficData trafficData;

    public CarToService createCarToServiceFromDealer(CarToSell carFromDealer) {

        return CarToService.builder()
                        .vin(carFromDealer.getVin())
                        .brand(carFromDealer.getBrand())
                        .model(carFromDealer.getModel())
                        .year(carFromDealer.getYear())
                        .build();
    }

    public CarToService createCarToServiceFromOutside(String vin) {

        return trafficData.getCarOuterServiceRequestList().stream()
                .filter(string -> string.contains(vin))
                .map(string -> string.split(";"))
                .map(arr -> CarToService.builder()
                        .vin(arr[1])
                        .brand(arr[2])
                        .model(arr[3])
                        .year(Short.valueOf(arr[4]))
                        .build()).toList().get(0);
    }
}
