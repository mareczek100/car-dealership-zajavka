package pl.mareczek100.service;

import lombok.Value;
import org.springframework.stereotype.Service;
import pl.mareczek100.domain.CarToSell;
import pl.mareczek100.domain.Invoice;

@Value
@Service
public class PurchaseCarService {

    CarToSellService carToSellService;
    InvoiceService invoiceService;

    public Invoice buyANewCar(String vin) {
        carToSellService.findAvailableCarToSell(vin);
        CarToSell carToSell = carToSellService.findCarToSell(vin);
        String invoiceNumber = invoiceService.createInvoice(carToSell.getVin());
        Invoice invoice = invoiceService.findInvoice(invoiceNumber);
        carToSellService.deleteFromAvailableCar(vin);

        return invoice;
    }


}
