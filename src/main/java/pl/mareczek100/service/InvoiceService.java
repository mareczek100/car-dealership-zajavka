package pl.mareczek100.service;

import lombok.Value;
import org.springframework.stereotype.Service;
import pl.mareczek100.infrastructure.data_storage.InvoiceDataStorage;
import pl.mareczek100.infrastructure.database.entity.CarToSell;
import pl.mareczek100.infrastructure.database.entity.Invoice;
import pl.mareczek100.service.dao.InvoiceRepository;

import java.util.List;

@Value
@Service
public class InvoiceService {

    InvoiceDataStorage invoiceDataStorage;
    InvoiceRepository invoiceRepository;
    CarToSellService carToSellService;

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
        List<Invoice> allInvoices = invoiceRepository.findAllInvoices();
        if (allInvoices.isEmpty()){
            throw  new RuntimeException("No invoice's at all!");
        }
        return allInvoices;
    }

    public Invoice buyANewCar(String vin) {
        carToSellService.findAvailableCarToSell(vin);
        CarToSell carToSell = carToSellService.findCarToSell(vin);
        String invoiceNumber = createInvoice(carToSell.getVin());
        Invoice invoice = findInvoice(invoiceNumber);
        carToSellService.deleteFromAvailableCar(vin);

        return invoice;
    }
}
