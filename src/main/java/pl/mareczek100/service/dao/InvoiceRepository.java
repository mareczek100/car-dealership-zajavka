package pl.mareczek100.service.dao;

import pl.mareczek100.infrastructure.database.entity.Invoice;

import java.util.List;
import java.util.Optional;


public interface InvoiceRepository {


    void insertInvoice(Invoice invoice);

    Optional<Invoice> findInvoice(String invoiceNumber);

    List<Invoice> findAllInvoices();
}
