package pl.mareczek100.infrastructure.data_storage;

import lombok.Value;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.database.entity.CarToSellEntity;
import pl.mareczek100.infrastructure.database.entity.CustomerEntity;
import pl.mareczek100.infrastructure.database.entity.InvoiceEntity;
import pl.mareczek100.infrastructure.database.entity.SalesmanEntity;
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

    public InvoiceEntity createInvoice(String vin) {
        List<String> invoiceList = trafficData.getInvoiceList();
        List<String> invoiceListAgain = trafficData.getInvoiceListAgain();
        invoiceList.addAll(invoiceListAgain);

        InvoiceEntity invoiceEntity = invoiceList.stream()
                .filter(invoiceParameter -> invoiceParameter.contains(vin))
                .map(string -> string.split(";"))
                .map(arr -> InvoiceEntity.builder()
                        .invoiceNumber(UUID.randomUUID().toString())
                        .dateTime(OffsetDateTime.now())
                        .carToSellEntity(getCarToSell(arr[1]))
                        .customerEntity(getNewOrFindCustomerToMakeInvoice(arr[0]))
                        .salesmanEntity(getSalesman(arr[2]))
                        .build())
                .toList().get(0);

        invoiceEntity.getCarToSellEntity().setInvoiceEntity(invoiceEntity);
        invoiceEntity.getCustomerEntity().setInvoiceEntities(Set.of(invoiceEntity));
        invoiceEntity.getSalesmanEntity().setInvoiceEntities(Set.of(invoiceEntity));

        return invoiceEntity;
    }

    private SalesmanEntity getSalesman(String pesel) {
        return salesmanService.findSalesman(pesel);
    }

    private CarToSellEntity getCarToSell(String vin) {
        return carToSellService.findCarToSell(vin);
    }

    private CustomerEntity getNewOrFindCustomerToMakeInvoice(String email) {
        return customerService.createNewOrFindCustomerToMakeInvoice(email);
    }
}
