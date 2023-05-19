package pl.mareczek100.infrastructure.data_storage;

import lombok.Value;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.database.entity.CarToSell;
import pl.mareczek100.infrastructure.database.entity.Customer;
import pl.mareczek100.infrastructure.database.entity.Invoice;
import pl.mareczek100.infrastructure.database.entity.Salesman;
import pl.mareczek100.service.CarToSellService;
import pl.mareczek100.service.CustomerService;
import pl.mareczek100.service.SalesmanService;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Value
@Repository
public class InvoiceDataStorage {

    TrafficData trafficData;
    CarToSellService carToSellService;
    CustomerService customerService;
    SalesmanService salesmanService;

    public Invoice createInvoice(String vin) {
        List<String> invoiceList = trafficData.getInvoiceList();
        List<String> invoiceListAgain = trafficData.getInvoiceListAgain();
        invoiceList.addAll(invoiceListAgain);

        Invoice invoice = invoiceList.stream()
                .filter(invoiceParameter -> invoiceParameter.contains(vin))
                .map(string -> string.split(";"))
                .map(arr -> Invoice.builder()
                        .invoiceNumber(UUID.randomUUID().toString())
                        .dateTime(OffsetDateTime.now())
                        .carToSell(getCarToSell(arr[1]))
                        .customer(getNewOrFindCustomerToMakeInvoice(arr[0]))
                        .salesman(getSalesman(arr[2]))
                        .build())
                .toList().get(0);

        invoice.getCarToSell().setInvoice(invoice);
        invoice.getCustomer().setInvoices(Set.of(invoice));
        invoice.getSalesman().setInvoices(Set.of(invoice));

        return invoice;
    }

    private Salesman getSalesman(String pesel) {
        return salesmanService.findSalesman(pesel);
    }

    private CarToSell getCarToSell(String vin) {
        return carToSellService.findCarToSell(vin);
    }

    private Customer getNewOrFindCustomerToMakeInvoice(String email) {
        return customerService.createNewOrFindCustomerToMakeInvoice(email);
    }
}
