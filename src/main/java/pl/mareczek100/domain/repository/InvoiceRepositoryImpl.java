package pl.mareczek100.domain.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.mareczek100.domain.Invoice;
import pl.mareczek100.infrastructure.database.entity.InvoiceEntity;
import pl.mareczek100.infrastructure.database.entityMapper.InvoiceEntityMapper;
import pl.mareczek100.infrastructure.database.jpaRepository.InvoiceJpaRepository;
import pl.mareczek100.service.dao.InvoiceRepository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class InvoiceRepositoryImpl implements InvoiceRepository {

    private final InvoiceJpaRepository invoiceJpaRepository;
    private final InvoiceEntityMapper invoiceEntityMapper;
    @Override
    public Invoice insertInvoice(Invoice invoice) {
        InvoiceEntity invoiceEntity = invoiceEntityMapper.mapToEntity(invoice);
        InvoiceEntity invoiceEntitySaved = invoiceJpaRepository.saveAndFlush(invoiceEntity);
        return invoiceEntityMapper.mapFromEntity(invoiceEntitySaved);
    }
    @Override
    public Optional<Invoice> findInvoiceByInvoiceNumber(String invoiceNumber) {
        return invoiceJpaRepository.findInvoiceByInvoiceNumber(invoiceNumber)
                .map(invoiceEntityMapper::mapFromEntity);
    }

    @Override
    public List<Invoice> findInvoiceByEmail(String email) {
        return invoiceJpaRepository.findInvoiceByEmail(email).stream()
                .map(invoiceEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public Optional<Invoice> findInvoiceByVin(String vin) {
        return invoiceJpaRepository.findInvoiceByVin(vin)
                .map(invoiceEntityMapper::mapFromEntity);
    }

    @Override
    public List<Invoice> findAllInvoices() {
        return invoiceJpaRepository.findAll().stream()
                .map(invoiceEntityMapper::mapFromEntity)
                .toList();
    }
}
