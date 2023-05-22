package pl.mareczek100.infrastructure.database.entityMapper;

import pl.mareczek100.domain.Invoice;
import pl.mareczek100.infrastructure.database.entity.InvoiceEntity;

public interface InvoiceEntityMapper {
    Invoice mapFromEntity(InvoiceEntity invoiceEntity);
    InvoiceEntity mapToEntity(Invoice invoice);

}
