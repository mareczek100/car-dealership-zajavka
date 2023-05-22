package pl.mareczek100.domain.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.mareczek100.domain.CarToSell;
import pl.mareczek100.infrastructure.database.entityMapper.CarToSellEntityMapper;
import pl.mareczek100.infrastructure.database.jpaRepository.CarToSellJpaRepository;
import pl.mareczek100.service.dao.CarToSellRepository;

import java.util.List;
import java.util.Optional;


@Repository
@AllArgsConstructor
public class CarToSellRepositoryImpl implements CarToSellRepository {

    private final CarToSellJpaRepository carToSellJpaRepository;
    private final CarToSellEntityMapper carToSellMapper;

    @Override
    public Optional<CarToSell> findCarToSell(String vin) {
        return carToSellJpaRepository.findByVin(vin)
                .map(carToSellMapper::mapFromEntity);
    }

    @Override
    public List<CarToSell> findAllCarsToSell() {
        return carToSellJpaRepository.findAll().stream()
                .map(carToSellMapper::mapFromEntity)
                .toList();
    }

    @Override
    public void deleteCar(String vin) {
        carToSellJpaRepository.deleteByVin(vin);
    }


}