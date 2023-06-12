package pl.mareczek100.infrastructure.database.entityMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.mareczek100.domain.Mechanic;
import pl.mareczek100.infrastructure.database.entity.MechanicEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MechanicEntityMapper {
    @Mapping(target = "carServiceHandlings", ignore = true)
    Mechanic mapFromEntity(MechanicEntity mechanicEntity);
    MechanicEntity mapToEntity(Mechanic mechanic);

}
