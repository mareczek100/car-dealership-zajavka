package pl.mareczek100.service.dao;

import pl.mareczek100.infrastructure.database.entity.InvoiceEntity;

import java.util.List;
import java.util.Optional;


public interface InvoiceRepository {


    void insertInvoice(InvoiceEntity invoiceEntity);

    Optional<InvoiceEntity> findInvoice(String invoiceNumber);

    List<InvoiceEntity> findAllInvoices();
}
