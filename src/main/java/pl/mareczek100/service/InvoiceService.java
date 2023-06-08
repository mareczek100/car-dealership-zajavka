package pl.mareczek100.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mareczek100.domain.Invoice;
import pl.mareczek100.service.dao.InvoiceRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final CustomerService customerService;

    public Invoice createInvoiceWithCustomer(String vin) {
//        return createInvoice(vin);
        return null;
    }

    @Transactional
    public Invoice findInvoiceByInvoiceNumber(String invoiceNumber) {
        return invoiceRepository.findInvoiceByInvoiceNumber(invoiceNumber)
                .orElseThrow(() -> new RuntimeException("Sorry, Your Invoice didn't exist!"));
    }

    @Transactional
    public List<Invoice> findInvoiceByEmail(String email) {
        customerService.findCustomer(email);
        return invoiceRepository.findInvoiceByEmail(email);
    }

    @Transactional
    public List<Invoice> findAllInvoices() {
        List<Invoice> allInvoiceEntities = invoiceRepository.findAllInvoices();
        if (allInvoiceEntities.isEmpty()) {
            throw new RuntimeException("No invoice's at all!");
        }
        return allInvoiceEntities;
    }

}
