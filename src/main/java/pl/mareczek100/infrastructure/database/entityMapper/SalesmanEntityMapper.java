package pl.mareczek100.infrastructure.database.entityMapper;

import pl.mareczek100.domain.Salesman;
import pl.mareczek100.infrastructure.database.entity.SalesmanEntity;

public interface SalesmanEntityMapper {
    Salesman mapFromEntity(SalesmanEntity salesmanEntity);
    SalesmanEntity mapToEntity(Salesman salesman);

}