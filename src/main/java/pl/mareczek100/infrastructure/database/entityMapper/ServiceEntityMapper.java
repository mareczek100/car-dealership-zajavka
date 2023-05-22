package pl.mareczek100.infrastructure.database.entityMapper;

import pl.mareczek100.domain.Service;
import pl.mareczek100.infrastructure.database.entity.ServiceEntity;


public interface ServiceEntityMapper {
    Service mapFromEntity(ServiceEntity serviceEntity);
    ServiceEntity mapToEntity(Service service);

}
