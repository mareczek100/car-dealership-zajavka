package pl.mareczek100.infrastructure.data_storage;

import lombok.Value;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.database.entity.CarToSellTempStorage;
import pl.mareczek100.service.dao.CarToSellRepository;

import java.util.List;

@Value
@Repository
public class CarToSellTempStorageDataStorage {
    CarToSellRepository carToSellRepository;
    public List<CarToSellTempStorage> createCarToSellTempStorage() {
        return carToSellRepository.findAllCarsToSell().stream()
                .map(carToSell -> CarToSellTempStorage.builder()
                        .carToSellTempStorageId(carToSell.getCarToSellId())
                        .vin(carToSell.getVin())
                        .brand(carToSell.getBrand())
                        .model(carToSell.getModel())
                        .year(carToSell.getYear())
                        .color(carToSell.getColor())
                        .price(carToSell.getPrice())
                        .build())
                .toList();
    }

}