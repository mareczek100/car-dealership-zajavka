package pl.mareczek100.infrastructure.testInputData;

import pl.mareczek100.infrastructure.database.entity.CarToSellEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TestInputData {

    public static List<CarToSellEntity> exampleCars() {
        List<CarToSellEntity> cars = new ArrayList<>();
        CarToSellEntity carMercedes = CarToSellEntity.builder()
                .vin("1")
                .brand("Mercedes")
                .model("a")
                .year((short) 2000)
                .color("a")
                .price(BigDecimal.valueOf(10000))
                .build();
        CarToSellEntity carFord = CarToSellEntity.builder()
                .vin("2")
                .brand("Ford")
                .model("a")
                .year((short) 2000)
                .color("a")
                .price(BigDecimal.valueOf(10000))
                .build();
        CarToSellEntity carDacia = CarToSellEntity.builder()
                .vin("3")
                .brand("Dacia")
                .model("a")
                .year((short) 2000)
                .color("a")
                .price(BigDecimal.valueOf(10000))
                .build();

        cars.add(carMercedes);
        cars.add(carFord);
        cars.add(carDacia);

        return cars;
    }


}
