package pl.mareczek100.infrastructure.database.entityMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.mareczek100.domain.Invoice;
import pl.mareczek100.infrastructure.database.entity.InvoiceEntity;
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InvoiceEntityMapper {
    @Mapping(target = "customer.invoices", ignore = true)
    @Mapping(target = "customer.address", ignore = true)
    @Mapping(target = "customer.carServiceRequests", ignore = true)
    @Mapping(target = "carToSell.invoice", ignore = true)
    @Mapping(target = "salesman.invoices", ignore = true)
    Invoice mapFromEntity(InvoiceEntity invoiceEntity);
//    @Mapping(target = "customer.invoices", ignore = true)
//    @Mapping(target = "customer.address", ignore = true)
//    @Mapping(target = "customer.carServiceRequests", ignore = true)
//    @Mapping(target = "carToSell.invoice", ignore = true)
//    @Mapping(target = "salesman.invoices", ignore = true)
    InvoiceEntity mapToEntity(Invoice invoice);

}
