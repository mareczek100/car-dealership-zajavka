package pl.mareczek100.service;

import lombok.Value;
import org.springframework.stereotype.Service;
import pl.mareczek100.domain.CarToSell;
import pl.mareczek100.domain.Invoice;
import pl.mareczek100.domain.inputTrafficData.data_storage.InvoiceDataStorage;
import pl.mareczek100.service.dao.InvoiceRepository;

import java.util.List;

@Value
@Service
public class InvoiceService {

    InvoiceDataStorage invoiceDataStorage;
    InvoiceRepository invoiceRepository;

    public String createInvoice(String vin) {
        Invoice invoice = invoiceDataStorage.createInvoice(vin);
        invoiceRepository.insertInvoice(invoice);
        return invoice.getInvoiceNumber();
    }

    public Invoice findInvoice(String invoiceNumber) {
        return invoiceRepository.findInvoice(invoiceNumber)
                .orElseThrow(() -> new RuntimeException("Sorry, Your Invoice didn't exist!"));
    }

    public List<Invoice> findAllInvoices() {
        List<Invoice> allInvoiceEntities = invoiceRepository.findAllInvoices();
        if (allInvoiceEntities.isEmpty()){
            throw  new RuntimeException("No invoice's at all!");
        }
        return allInvoiceEntities;
    }

}
