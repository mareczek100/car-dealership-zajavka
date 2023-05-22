package pl.mareczek100.infrastructure.database.entityMapper;

import pl.mareczek100.domain.CarServiceRequest;
import pl.mareczek100.infrastructure.database.entity.CarServiceRequestEntity;


public interface CarServiceRequestEntityMapper {
    CarServiceRequest mapFromEntity(CarServiceRequestEntity carServiceRequestEntity);
    CarServiceRequestEntity mapToEntity(CarServiceRequest carServiceRequest);


}
