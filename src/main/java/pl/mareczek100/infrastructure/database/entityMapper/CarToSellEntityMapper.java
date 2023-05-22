package pl.mareczek100.infrastructure.database.entityMapper;

import pl.mareczek100.domain.CarToSell;
import pl.mareczek100.infrastructure.database.entity.CarToSellEntity;


public interface CarToSellEntityMapper {
    CarToSell mapFromEntity(CarToSellEntity carToSellEntity);
    CarToSellEntity mapToEntity(CarToSell carToSell);

}