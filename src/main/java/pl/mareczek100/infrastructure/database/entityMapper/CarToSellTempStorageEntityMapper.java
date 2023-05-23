package pl.mareczek100.infrastructure.database.entityMapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.mareczek100.domain.CarToSellTempStorage;
import pl.mareczek100.infrastructure.database.entity.CarToSellTempStorageEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarToSellTempStorageEntityMapper {
    CarToSellTempStorage mapFromEntity(CarToSellTempStorageEntity carToSellTempStorageEntity);
    CarToSellTempStorageEntity mapToEntity(CarToSellTempStorage carToSellTempStorage);
}