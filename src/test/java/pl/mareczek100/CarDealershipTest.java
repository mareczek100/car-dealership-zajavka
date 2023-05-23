package pl.mareczek100;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.mareczek100.domain.CarServiceRequest;
import pl.mareczek100.domain.Invoice;
import pl.mareczek100.infrastructure.configuration.AppBeansConfig;
import pl.mareczek100.service.*;

import java.util.List;
import java.util.Objects;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AllArgsConstructor(onConstructor_ = @Autowired)
@SpringJUnitConfig(classes = AppBeansConfig.class)
@Slf4j
class CarDealershipTest {

    @Container
    static PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:15.0");
    @DynamicPropertySource
    static void postgresqlContainerProperties(DynamicPropertyRegistry registry) {
        registry.add("jdbc.url", () -> POSTGRESQL_CONTAINER.getJdbcUrl());
        registry.add("jdbc.user", () -> POSTGRESQL_CONTAINER.getUsername());
        registry.add("jdbc.pass", () -> POSTGRESQL_CONTAINER.getPassword());
    }
    private final AddressService addressService;
    private final SalesmanService salesmanService;
    private final MechanicService mechanicService;
    private final CustomerService customerService;
    private final CarToSellService carToSellService;
    private final CarToSellTempStorageService carToSellTempStorageService;
    private final ServiceService serviceService;
    private final PartService partService;
    private final InvoiceService invoiceService;
    private final CarServiceRequestService carServiceRequestService;
    private final ServiceRequestProcessingService requestProcessingService;
    private final PurchaseCarService purchaseCarService;
    private final CarToServiceService carToServiceService;

    @BeforeEach
    void setUp() {
        Assertions.assertNotNull(addressService);
        Assertions.assertNotNull(carServiceRequestService);
        Assertions.assertNotNull(carToSellService);
        Assertions.assertNotNull(carToSellTempStorageService);
        Assertions.assertNotNull(carToServiceService);
        Assertions.assertNotNull(customerService);
        Assertions.assertNotNull(invoiceService);
        Assertions.assertNotNull(mechanicService);
        Assertions.assertNotNull(partService);
        Assertions.assertNotNull(purchaseCarService);
        Assertions.assertNotNull(salesmanService);
        Assertions.assertNotNull(requestProcessingService);
        Assertions.assertNotNull(serviceService);
    }


    @Test
    @Order(1)
    void purchase() {
        log.info("#### RUNNING ORDER 1 ####");
        for (int i = 0; i < carToSellService.findAllCarsToSell().size(); i++) {
            System.out.println(purchaseCarService.buyANewCar(carToSellService.findAllCarsToSell().get(i).getVin()));
        }
    }

    @Test
    @Order(2)
    void createServiceRequest() {
        log.info("#### RUNNING ORDER 2 ####");
        carServiceRequestService.createCarServiceRequestInner();
        carServiceRequestService.createCarServiceRequestOuter();

//        carServiceRequestService.findAllCarServiceRequest();
    }

    @Test
    @Order(3)
    void processServiceRequest() {
        log.info("#### RUNNING ORDER 3 ####");
        requestProcessingService.serviceRequestProcess();
    }

    @Test
    @Order(4)
    void printCarServiceHistory() {
        log.info("#### RUNNING ORDER 4 ####");
//        carServiceRequestService.findCarServiceRequestHistory("1GCEC19X27Z109567");
//        carServiceRequestService.findCarServiceRequestHistory("2C3CDYAG2DH731952");
//        carServiceRequestService.findCarServiceRequestHistory("1N4BA41E18C806520");
        carToServiceService.printCarHistory("1GCEC19X27Z109567");
        carToServiceService.printCarHistory("2C3CDYAG2DH731952");
        carToServiceService.printCarHistory("1N4BA41E18C806520");
    }

    @Test
    @Order(5)
    void verify() {
        log.info("#### RUNNING ORDER 5 ####");
        List<Invoice> allInvoices = invoiceService.findAllInvoices();
        List<CarServiceRequest> allCarServiceRequest = carServiceRequestService.findAllCarServiceRequest();

        Assertions.assertEquals(6, allInvoices.size());
        Assertions.assertEquals(3, allCarServiceRequest.size());
        Assertions.assertEquals(2, allCarServiceRequest.stream()
                .filter(carServiceRequest ->
                        Objects.nonNull(carServiceRequest.getCompletedDateTime())).toList().size());
        Assertions.assertEquals(5, allCarServiceRequest.stream()
                .map(CarServiceRequest::getCarServiceHandling).toList().size());
        Assertions.assertEquals(4, allCarServiceRequest.stream()
                .map(CarServiceRequest::getCarServicePart).toList().size());
    }
}