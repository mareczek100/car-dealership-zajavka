package pl.mareczek100;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.mareczek100.infrastructure.configuration.AppBeansConfig;
import pl.mareczek100.service.*;


public class CarDealership {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppBeansConfig.class);
        SalesmanService salesmanService = context.getBean(SalesmanService.class);
        MechanicService mechanicService = context.getBean(MechanicService.class);
        CarToSellService carToSellService = context.getBean(CarToSellService.class);
        ServiceService serviceService = context.getBean(ServiceService.class);
        PartService partService = context.getBean(PartService.class);
        InvoiceService invoiceService = context.getBean(InvoiceService.class);
        AddressService addressService = context.getBean(AddressService.class);
        CustomerService customerService = context.getBean(CustomerService.class);
        PurchaseCarService purchaseCarService = context.getBean(PurchaseCarService.class);
        CarServiceRequestService carServiceRequestService = context.getBean(CarServiceRequestService.class);
        ServiceRequestProcessingService requestProcessingService = context.getBean(ServiceRequestProcessingService.class);

        for (int i = 0; i < carToSellService.findAllCarsToSell().size(); i++) {
            System.out.println(purchaseCarService.buyANewCar(carToSellService.findAllCarsToSell().get(i).getVin()));
        }
        carServiceRequestService.createCarServiceRequestInner();
        carServiceRequestService.createCarServiceRequestOuter();
        requestProcessingService.serviceRequestProcess();

        carServiceRequestService.findAllCarServiceRequest();
        carServiceRequestService.findCarServiceRequestHistory("2C3CDYAG2DH731952");

//        System.out.println(customerService.findCustomer("alf.samoch@gmail.com").getCarServiceRequests());

    }
}