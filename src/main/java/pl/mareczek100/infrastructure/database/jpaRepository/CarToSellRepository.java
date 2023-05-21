package pl.mareczek100.infrastructure.database.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mareczek100.infrastructure.database.entity.CarToSellEntity;

import java.util.List;
import java.util.Optional;


public interface CarToSellRepository extends JpaRepository<Object, Integer> {


    void carToSellInit(CarToSellEntity carToSellEntity);

    Optional<CarToSellEntity> findCarToSell(String vin);

    List<CarToSellEntity> findAllCarsToSell();

    void deleteCar(String vin);
}