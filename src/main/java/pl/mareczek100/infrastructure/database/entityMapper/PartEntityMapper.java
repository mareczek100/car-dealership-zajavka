package pl.mareczek100.infrastructure.database.entityMapper;

import pl.mareczek100.domain.Part;
import pl.mareczek100.infrastructure.database.entity.PartEntity;

public interface PartEntityMapper {
    Part mapFromEntity(PartEntity partEntity);
    PartEntity mapToEntity(Part part);

}
