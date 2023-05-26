package pl.mareczek100.infrastructure.database.entityMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.mareczek100.domain.Part;
import pl.mareczek100.infrastructure.database.entity.PartEntity;
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PartEntityMapper {
//    @Mapping(target = "carServiceParts", ignore = true)
    Part mapFromEntity(PartEntity partEntity);
    PartEntity mapToEntity(Part part);

}