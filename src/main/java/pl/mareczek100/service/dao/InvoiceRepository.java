package pl.mareczek100.service.dao;

import pl.mareczek100.domain.Invoice;

import java.util.List;
import java.util.Optional;


public interface InvoiceRepository {

    Optional<Invoice> findInvoice(String invoiceNumber);

    List<Invoice> findAllInvoices();
}
