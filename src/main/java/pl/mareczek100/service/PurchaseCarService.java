package pl.mareczek100.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mareczek100.domain.CarToSell;
import pl.mareczek100.domain.Invoice;
import pl.mareczek100.service.dao.PurchaseCarServiceRepository;

@Service
@AllArgsConstructor
public class PurchaseCarService {

    private final PurchaseCarServiceRepository purchaseCarServiceRepository;
    private final CarToSellService carToSellService;
    private final InvoiceService invoiceService;

    @Transactional
    public Invoice buyANewCar(String vin) {
        carToSellService.findAvailableCarToSell(vin);
        CarToSell carToSell = carToSellService.findCarToSell(vin);
        Invoice invoice = invoiceService.createInvoiceWithCustomer(carToSell.getVin());
        Invoice savedInvoice = purchaseCarServiceRepository.insertInvoice(invoice);
        carToSellService.deleteFromAvailableCar(vin);
        return savedInvoice;
    }

}