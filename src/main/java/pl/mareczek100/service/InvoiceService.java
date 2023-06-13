package pl.mareczek100.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mareczek100.domain.CarToSell;
import pl.mareczek100.domain.Customer;
import pl.mareczek100.domain.Invoice;
import pl.mareczek100.domain.Salesman;
import pl.mareczek100.service.dao.InvoiceRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final CustomerService customerService;

    public Invoice createInvoice(Customer customer, CarToSell carToSell, Salesman salesman) {
        return Invoice.builder()
                .invoiceNumber(UUID.randomUUID().toString())
                .dateTime(OffsetDateTime.now())
                .carToSell(carToSell)
                .customer(customer)
                .salesman(salesman)
                .build();
    }

    @Transactional
    public Invoice insertInvoice(Invoice invoice) {
        return invoiceRepository.insertInvoice(invoice);
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
    public Invoice findInvoiceByVin(String vin) {
        return invoiceRepository.findInvoiceByVin(vin).orElseThrow(
                () -> new RuntimeException("Car [%s] is not bought!!".formatted(vin))
        );
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
