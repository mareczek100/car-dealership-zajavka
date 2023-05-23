package pl.mareczek100.infrastructure.database.entityMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.mareczek100.domain.Salesman;
import pl.mareczek100.infrastructure.database.entity.SalesmanEntity;
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SalesmanEntityMapper {
    @Mapping(target = "invoices", ignore = true)
    Salesman mapFromEntity(SalesmanEntity salesmanEntity);
    SalesmanEntity mapToEntity(Salesman salesman);

}