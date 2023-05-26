package pl.mareczek100.infrastructure.database.entityMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.mareczek100.domain.CarToSell;
import pl.mareczek100.infrastructure.database.entity.CarToSellEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarToSellEntityMapper {
    @Mapping(target = "invoice", ignore = true)
    CarToSell mapFromEntity(CarToSellEntity carToSellEntity);

    @Mapping(target = "invoice", ignore = true)
    CarToSellEntity mapToEntity(CarToSell carToSell);
}