package pl.mareczek100.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mareczek100.domain.Invoice;
import pl.mareczek100.domain.inputTrafficData.data_storage.InvoiceDataStorage;
import pl.mareczek100.service.dao.InvoiceRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class InvoiceService {

    private final InvoiceDataStorage invoiceDataStorage;
    private final InvoiceRepository invoiceRepository;

    public Invoice createInvoiceWithCustomer(String vin) {
        return invoiceDataStorage.createInvoice(vin);
    }
    @Transactional
    public Invoice findInvoice(String invoiceNumber) {
        return invoiceRepository.findInvoice(invoiceNumber)
                .orElseThrow(() -> new RuntimeException("Sorry, Your Invoice didn't exist!"));
    }
    @Transactional
    public List<Invoice> findAllInvoices() {
        List<Invoice> allInvoiceEntities = invoiceRepository.findAllInvoices();
        if (allInvoiceEntities.isEmpty()){
            throw  new RuntimeException("No invoice's at all!");
        }
        return allInvoiceEntities;
    }

}
