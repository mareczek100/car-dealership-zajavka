package pl.mareczek100.domain.inputTrafficData.data_storage;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.mareczek100.domain.CarToSell;
import pl.mareczek100.domain.Customer;
import pl.mareczek100.domain.Invoice;
import pl.mareczek100.domain.Salesman;
import pl.mareczek100.service.CarToSellService;
import pl.mareczek100.service.CustomerService;
import pl.mareczek100.service.SalesmanService;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class InvoiceDataStorage {

    private final TrafficData trafficData;
    private final CarToSellService carToSellService;
    private final CustomerService customerService;
    private final SalesmanService salesmanService;

    public Invoice createInvoice(String vin) {
        List<String> invoiceList = trafficData.getInvoiceList();
        List<String> invoiceListAgain = trafficData.getInvoiceListAgain();
        invoiceList.addAll(invoiceListAgain);

        return invoiceList.stream()
                .filter(line -> line.contains(vin))
                .map(string -> string.split(";"))
                .map(arr -> Invoice.builder()
                        .invoiceNumber(UUID.randomUUID().toString())
                        .dateTime(OffsetDateTime.now())
                        .carToSell(getCarToSell(arr[1]))
                        .customer(getNewOrFindCustomerToMakeInvoice(arr[0]))
                        .salesman(getSalesman(arr[2]))
                        .build())
                .toList().get(0);

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
