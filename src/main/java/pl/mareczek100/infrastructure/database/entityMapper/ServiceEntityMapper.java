package pl.mareczek100.infrastructure.database.entityMapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.mareczek100.domain.Service;
import pl.mareczek100.infrastructure.database.entity.ServiceEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServiceEntityMapper {
//    @Mapping(target = "carServiceHandlings", ignore = true)
    Service mapFromEntity(ServiceEntity serviceEntity);
    ServiceEntity mapToEntity(Service service);

}
