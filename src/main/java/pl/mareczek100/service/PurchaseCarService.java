package pl.mareczek100.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mareczek100.domain.CarToSell;
import pl.mareczek100.domain.Customer;
import pl.mareczek100.domain.Invoice;
import pl.mareczek100.domain.Salesman;

@Service
@AllArgsConstructor
public class PurchaseCarService {

    private final CarToSellService carToSellService;
    private final CustomerService customerService;
    private final SalesmanService salesmanService;
    private final InvoiceService invoiceService;

    @Transactional
    public Invoice buyANewCar(String vin, String email) {
        CarToSell carToSell = carToSellService.findCarToSell(vin);
        Customer customer = customerService.findCustomer(email);
        Salesman salesman = salesmanService.findAllSalesman().stream().findAny().get();
        Invoice invoice = invoiceService.createInvoice(customer, carToSell, salesman);
        Invoice savedInvoice = invoiceService.insertInvoice(invoice);
        carToSellService.deleteFromAvailableCar(vin);
        return savedInvoice;
    }
}