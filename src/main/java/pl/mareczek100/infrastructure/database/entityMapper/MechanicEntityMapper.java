package pl.mareczek100.infrastructure.database.entityMapper;

import pl.mareczek100.domain.Mechanic;
import pl.mareczek100.infrastructure.database.entity.MechanicEntity;


public interface MechanicEntityMapper {
    Mechanic mapFromEntity(MechanicEntity mechanicEntity);
    MechanicEntity mapToEntity(Mechanic mechanic);

}
