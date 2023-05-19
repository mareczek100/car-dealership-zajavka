package pl.mareczek100;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.mareczek100.infrastructure.configuration.AppBeansConfig;
import pl.mareczek100.infrastructure.configuration.HibernateConfig;
import pl.mareczek100.infrastructure.database.TableCreator;
import pl.mareczek100.service.*;


public class CarDealership {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppBeansConfig.class);
        TableCreator tableCreator = context.getBean(TableCreator.class);
        SalesmanService salesmanService = context.getBean(SalesmanService.class);
        MechanicService mechanicService = context.getBean(MechanicService.class);
        CarToSellService carToSellService = context.getBean(CarToSellService.class);
        ServiceService serviceService = context.getBean(ServiceService.class);
        PartService partService = context.getBean(PartService.class);
        InvoiceService invoiceService = context.getBean(InvoiceService.class);
        AddressService addressService = context.getBean(AddressService.class);
        CustomerService customerService = context.getBean(CustomerService.class);
        CarServiceRequestService carServiceRequestService = context.getBean(CarServiceRequestService.class);
        CarServiceHandlingService carServiceHandlingService = context.getBean(CarServiceHandlingService.class);

        tableCreator.createCleanTables();
        addressService.initAddress();
        salesmanService.salesmanInit();
        mechanicService.mechanicInit();
        carToSellService.carToSellInit();
        serviceService.serviceInit();
        partService.partInit();
        for (int i = 0; i < carToSellService.findAllCarsToSell().size(); i++) {
            System.out.println(invoiceService.buyANewCar(carToSellService.findAllCarsToSell().get(i).getVin()));
        }
        carServiceRequestService.createCarServiceRequest("alf.samoch@gmail.com");
        carServiceRequestService.createCarServiceRequest("tom.zim@gmail.com");
        carServiceRequestService.createCarServiceRequest("adr.paczk@gmail.com");

        carServiceHandlingService.carServiceHandlingInit();

        carServiceRequestService.findAllCarServiceRequest();
        carServiceRequestService.findCarServiceRequestHistory("2C3CDYAG2DH731952");

        System.out.println(customerService.findCustomer("alf.samoch@gmail.com").getCarServiceRequests());

        HibernateConfig.closeSessionFactory();
    }
}