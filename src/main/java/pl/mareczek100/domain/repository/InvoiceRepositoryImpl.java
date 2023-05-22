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
    public Optional<Invoice> findInvoice(String invoiceNumber) {
        return invoiceJpaRepository.findInvoiceByInvoiceNumber(invoiceNumber)
                .map(invoiceEntityMapper::mapFromEntity);
    }

    @Override
    public List<Invoice> findAllInvoices() {
        return invoiceJpaRepository.findAll().stream()
                .map(invoiceEntityMapper::mapFromEntity)
                .toList();
    }
}
