package pl.mareczek100;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import pl.mareczek100.infrastructure.configuration.AppBeansConfig;
import pl.mareczek100.infrastructure.configuration.HibernateConfig;
import pl.mareczek100.service.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AllArgsConstructor(onConstructor_ = @Autowired)
@SpringJUnitConfig(classes = AppBeansConfig.class)
@Slf4j
class CarDealershipTest {

    private final AddressService addressService;
    private final SalesmanService salesmanService;
    private final MechanicService mechanicService;
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
        Assertions.assertNotNull(salesmanService);
        Assertions.assertNotNull(mechanicService);
        Assertions.assertNotNull(carToSellService);
        Assertions.assertNotNull(carToSellTempStorageService);
        Assertions.assertNotNull(serviceService);
        Assertions.assertNotNull(partService);
        Assertions.assertNotNull(invoiceService);
        Assertions.assertNotNull(carServiceRequestService);
        Assertions.assertNotNull(requestProcessingService);
        Assertions.assertNotNull(purchaseCarService);
        Assertions.assertNotNull(carToServiceService);
    }

    @Test
    @Order(1)
    void purge() {
        log.info("#### RUNNING ORDER 1 ####");

    }

    @Test
    @Order(2)
    void init() {
        log.info("#### RUNNING ORDER 2 ####");

        carToSellService.carToSellInit();

    }

    @Test
    @Order(3)
    void purchase() {
        log.info("#### RUNNING ORDER 3 ####");
        for (int i = 0; i < carToSellService.findAllCarsToSell().size(); i++) {
            System.out.println(purchaseCarService.buyANewCar(carToSellService.findAllCarsToSell().get(i).getVin()));
        }
    }

    @Test
    @Order(4)
    void createServiceRequest() {
        log.info("#### RUNNING ORDER 4 ####");
        carServiceRequestService.createCarServiceRequestInner();
        carServiceRequestService.createCarServiceRequestOuter();

//        carServiceRequestService.findAllCarServiceRequest();
    }

    @Test
    @Order(5)
    void processServiceRequest() {
        log.info("#### RUNNING ORDER 5 ####");
        requestProcessingService.serviceRequestProcess();
    }

    @Test
    @Order(6)
    void printCarServiceHistory() {
        log.info("#### RUNNING ORDER 6 ####");
//        carServiceRequestService.findCarServiceRequestHistory("1GCEC19X27Z109567");
//        carServiceRequestService.findCarServiceRequestHistory("2C3CDYAG2DH731952");
//        carServiceRequestService.findCarServiceRequestHistory("1N4BA41E18C806520");
        carToServiceService.printCarHistory("1GCEC19X27Z109567");
        carToServiceService.printCarHistory("2C3CDYAG2DH731952");
        carToServiceService.printCarHistory("1N4BA41E18C806520");
    }

}