package pl.mareczek100.service.dao;

import pl.mareczek100.domain.Invoice;

public interface PurchaseCarServiceRepository {
    Invoice insertInvoice(Invoice invoice);
}
