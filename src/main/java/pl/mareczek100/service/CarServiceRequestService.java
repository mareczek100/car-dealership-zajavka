package pl.mareczek100.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mareczek100.domain.*;
import pl.mareczek100.service.dao.CarServiceRequestRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CarServiceRequestService {

    private final CarServiceRequestRepository carServiceRequestRepository;
    private final CarToSellService carToSellService;
    private final CarToServiceService carToServiceService;
    private final CustomerService customerService;
    private final InvoiceService invoiceService;

    @Transactional
    public void insertCarServiceRequest(CarServiceRequest carServiceRequest) {
        Customer customer = carServiceRequest.getCustomer();
        CarToService carToService = carServiceRequest.getCarToService();

        if (Objects.isNull(customer.getCustomerId())) {
            customerService.insertCustomer(customer);
        }

        CarToService existingCarToService = carToServiceService.findCarToService(carToService.getVin());

        if (Objects.isNull(existingCarToService)) {
            CarToService carToServiceSaved = carToServiceService.insertCarToService(carToService);
            CarServiceRequest serviceRequest = carServiceRequest.withCarToService(carToServiceSaved);
            carServiceRequestRepository.insertCarServiceRequest(serviceRequest);
        } else {
            CarServiceRequest serviceRequest = carServiceRequest.withCarToService(existingCarToService);
            carServiceRequestRepository.insertCarServiceRequest(serviceRequest);
        }
    }

    public CarServiceRequest createCarServiceRequestInner(String vin, String comment) {
        CarToSell carToSell = carToSellService.findCarToSell(vin);
        Invoice invoice = invoiceService.findInvoiceByVin(vin);

        return CarServiceRequest.builder()
                .carServiceRequestNumber(vin + UUID.randomUUID())
                .receivedDateTime(OffsetDateTime.now())
                .comment(comment)
                .customer(invoice.getCustomer())
                .carToService(
                        CarToService.builder()
                                .vin(carToSell.getVin())
                                .brand(carToSell.getBrand())
                                .model(carToSell.getModel())
                                .year(carToSell.getYear())
                                .build()
                )
                .build();
    }

    public CarServiceRequest createCarServiceRequestOuter(Customer customer, CarToService carToService, String comment) {

        return CarServiceRequest.builder()
                .carServiceRequestNumber(carToService.getVin() + UUID.randomUUID())
                .receivedDateTime(OffsetDateTime.now())
                .comment(comment)
                .customer(customer)
                .carToService(carToService)
                .build();

    }

    @Transactional
    public List<CarServiceRequest> findCarServiceRequests(String vin) {
        List<CarServiceRequest> requestsByCarVin = carServiceRequestRepository.findCarServiceRequestsByCarVin(vin);
        if (requestsByCarVin.isEmpty()) {
            throw new RuntimeException(
                    "Service request for car [%s] no exist".formatted(vin));
        }
        return requestsByCarVin;
    }

    @Transactional
    public CarServiceRequest findCarServiceRequest(String requestNumber) {
        return carServiceRequestRepository.findCarServiceRequestByCarServiceRequestNumber(requestNumber)
                .orElseThrow(() -> new RuntimeException(
                        "Service request [%s] no exist".formatted(requestNumber)));
    }

    @Transactional
    public List<CarServiceRequest> findAllCarServiceRequest() {
        return carServiceRequestRepository.findAllCarServiceRequest();
    }
}