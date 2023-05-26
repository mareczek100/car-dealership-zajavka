package pl.mareczek100.domain.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.mareczek100.domain.Invoice;
import pl.mareczek100.infrastructure.database.entity.InvoiceEntity;
import pl.mareczek100.infrastructure.database.entityMapper.InvoiceEntityMapper;
import pl.mareczek100.infrastructure.database.jpaRepository.InvoiceJpaRepository;
import pl.mareczek100.service.dao.PurchaseCarServiceRepository;

@Repository
@AllArgsConstructor
public class PurchaseCarServiceRepositoryImpl implements PurchaseCarServiceRepository {
    private final InvoiceJpaRepository invoiceJpaRepository;
    private final InvoiceEntityMapper invoiceEntityMapper;

    @Override
    public Invoice insertInvoice(Invoice invoice) {
        InvoiceEntity invoiceEntity = invoiceEntityMapper.mapToEntity(invoice);
        InvoiceEntity invoiceEntitySaved = invoiceJpaRepository.saveAndFlush(invoiceEntity);
        return invoiceEntityMapper.mapFromEntity(invoiceEntitySaved);
    }

}
