package pl.mareczek100.service;

import lombok.Value;
import org.springframework.stereotype.Service;
import pl.mareczek100.infrastructure.data_storage.InvoiceDataStorage;
import pl.mareczek100.infrastructure.database.entity.CarToSellEntity;
import pl.mareczek100.infrastructure.database.entity.InvoiceEntity;
import pl.mareczek100.service.dao.InvoiceRepository;

import java.util.List;

@Value
@Service
public class InvoiceService {

    InvoiceDataStorage invoiceDataStorage;
    InvoiceRepository invoiceRepository;
    CarToSellService carToSellService;

    public String createInvoice(String vin) {
        InvoiceEntity invoiceEntity = invoiceDataStorage.createInvoice(vin);
        invoiceRepository.insertInvoice(invoiceEntity);
        return invoiceEntity.getInvoiceNumber();
    }

    public InvoiceEntity findInvoice(String invoiceNumber) {
        return invoiceRepository.findInvoice(invoiceNumber)
                .orElseThrow(() -> new RuntimeException("Sorry, Your Invoice didn't exist!"));
    }

    public List<InvoiceEntity> findAllInvoices() {
        List<InvoiceEntity> allInvoiceEntities = invoiceRepository.findAllInvoices();
        if (allInvoiceEntities.isEmpty()){
            throw  new RuntimeException("No invoice's at all!");
        }
        return allInvoiceEntities;
    }

    public InvoiceEntity buyANewCar(String vin) {
        carToSellService.findAvailableCarToSell(vin);
        CarToSellEntity carToSellEntity = carToSellService.findCarToSell(vin);
        String invoiceNumber = createInvoice(carToSellEntity.getVin());
        InvoiceEntity invoiceEntity = findInvoice(invoiceNumber);
        carToSellService.deleteFromAvailableCar(vin);

        return invoiceEntity;
    }
}
