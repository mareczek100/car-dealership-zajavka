package pl.mareczek100.service;

import lombok.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mareczek100.domain.CarToSell;
import pl.mareczek100.domain.Invoice;
import pl.mareczek100.service.dao.PurchaseCarServiceRepository;

@Value
@Service
public class PurchaseCarService {
    PurchaseCarServiceRepository purchaseCarServiceRepository;
    CarToSellService carToSellService;
    InvoiceService invoiceService;

    @Transactional
    public Invoice buyANewCar(String vin) {
        carToSellService.findAvailableCarToSell(vin);
        CarToSell carToSell = carToSellService.findCarToSell(vin);
        Invoice invoice = invoiceService.createInvoice(carToSell.getVin());
        purchaseCarServiceRepository.buyANewCar(invoice);
        carToSellService.deleteFromAvailableCar(vin);
        return invoice;
    }

}
