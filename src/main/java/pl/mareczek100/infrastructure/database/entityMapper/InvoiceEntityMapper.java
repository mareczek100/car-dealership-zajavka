package pl.mareczek100.infrastructure.database.entityMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.mareczek100.domain.*;
import pl.mareczek100.infrastructure.database.entity.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InvoiceEntityMapper {
//    @Mapping(target = "customer.invoices", ignore = true)
////    @Mapping(target = "customer.address", ignore = true)
//    @Mapping(target = "customer.carServiceRequests", ignore = true)
//    @Mapping(target = "carToSell.invoice", ignore = true)
//    @Mapping(target = "salesman.invoices", ignore = true)
    default Invoice mapFromEntity(InvoiceEntity invoiceEntity){
    return Invoice.builder()
            .invoiceId(invoiceEntity.getInvoiceId())
            .invoiceNumber(invoiceEntity.getInvoiceNumber())
            .dateTime(invoiceEntity.getDateTime())
            .carToSell(getCarToSell(invoiceEntity.getCarToSell()))
            .customer(getCustomer(invoiceEntity.getCustomer()))
            .salesman(getSalesman(invoiceEntity.getSalesman()))
            .build();

}
    InvoiceEntity mapToEntity(Invoice invoice);


    private CarToSell getCarToSell(CarToSellEntity carToSellEntity){
        return CarToSell.builder()
                .carToSellId(carToSellEntity.getCarToSellId())
                .vin(carToSellEntity.getVin())
                .brand(carToSellEntity.getBrand())
                .model(carToSellEntity.getModel())
                .year(carToSellEntity.getYear())
                .color(carToSellEntity.getColor())
                .price(carToSellEntity.getPrice())
                .build();
    }

    private Customer getCustomer(CustomerEntity customerEntity){
        return Customer.builder()
                .customerId(customerEntity.getCustomerId())
                .name(customerEntity.getName())
                .surname(customerEntity.getSurname())
                .phone(customerEntity.getPhone())
                .email(customerEntity.getEmail())
                .address(getAddress(customerEntity.getAddress()))
                .build();
    }
    private Address getAddress(AddressEntity addressEntity) {
        return Address.builder()
                .addressId(addressEntity.getAddressId())
                .country(addressEntity.getCountry())
                .city(addressEntity.getCity())
                .postalCode(addressEntity.getPostalCode())
                .street(addressEntity.getStreet())
                .buildingFlatNumber(addressEntity.getBuildingFlatNumber())
                .build();
    }

    private Salesman getSalesman(SalesmanEntity salesmanEntity){
        return Salesman.builder()
                .salesmanId(salesmanEntity.getSalesmanId())
                .name(salesmanEntity.getName())
                .surname(salesmanEntity.getSurname())
                .pesel(salesmanEntity.getPesel())
                .build();
    }



}
