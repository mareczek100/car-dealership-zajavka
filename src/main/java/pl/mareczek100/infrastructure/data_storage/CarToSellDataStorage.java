package pl.mareczek100.infrastructure.data_storage;

import lombok.Value;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.database.entity.CarToSell;

import java.math.BigDecimal;
import java.util.List;

@Value
@Repository
public class CarToSellDataStorage {
    TrafficData trafficData;

    public List<CarToSell> createCarToSell() {
        return trafficData.getCarList().stream()
                .map(string -> string.split(";"))
                .map(arr -> CarToSell.builder()
                        .vin(arr[0])
                        .brand(arr[1])
                        .model(arr[2])
                        .year(Short.valueOf(arr[3]))
                        .color(arr[4])
                        .price(new BigDecimal(arr[5]))
                        .build())
                .toList();
    }

}