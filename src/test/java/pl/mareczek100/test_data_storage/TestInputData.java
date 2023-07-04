package pl.mareczek100.test_data_storage;

import pl.mareczek100.domain.Address;
import pl.mareczek100.domain.CarToSellTempStorage;
import pl.mareczek100.domain.Customer;
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

    public static CarToSellTempStorage availableCarToSell() {
        return CarToSellTempStorage.builder()
                .vin("1FT7X2B60FEA74019")
                .build();
    }

    public static Customer customerToInsert() {
        return Customer.builder()
                .name("Alfred")
                .surname("Samochodowy")
                .phone("+48 754 552 234")
                .email("alf.samoch@gmail.com")
                .address(Address.builder()
                        .country("Polska")
                        .city("Wrocław")
                        .postalCode("20-001")
                        .street("Bokserska")
                        .buildingFlatNumber("15")
                        .build())
                .build();
    }


}
