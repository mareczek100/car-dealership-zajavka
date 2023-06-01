package pl.mareczek100.infrastructure.database.entityMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.mareczek100.domain.*;
import pl.mareczek100.infrastructure.database.entity.AddressEntity;
import pl.mareczek100.infrastructure.database.entity.CarServiceRequestEntity;
import pl.mareczek100.infrastructure.database.entity.CustomerEntity;
import pl.mareczek100.infrastructure.database.entity.InvoiceEntity;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerEntityMapper {

    default Customer mapFromEntity(CustomerEntity customerEntity) {
        return Customer.builder()
                .customerId(customerEntity.getCustomerId())
                .name(customerEntity.getName())
                .surname(customerEntity.getSurname())
                .phone(customerEntity.getPhone())
                .email(customerEntity.getEmail())
                .address(getAddress(customerEntity.getAddress()))
                .carServiceRequests(getCarServiceRequests(customerEntity.getCarServiceRequests()))
                .invoices(getInvoices(customerEntity.getInvoices()))
                .build();

    }


    //    @Mapping(target = "carServiceRequests", ignore = true)
//    @Mapping(target = "invoices", ignore = true)
    @Mapping(target = "address.customers", ignore = true)
    CustomerEntity mapToEntity(Customer customer);

    private Set<CarServiceRequest> getCarServiceRequests(Set<CarServiceRequestEntity> carServiceRequestEntitySet) {
        return carServiceRequestEntitySet.stream().map(carServiceRequestEntity ->
                        CarServiceRequest.builder()
                                .carServiceRequestId(carServiceRequestEntity.getCarServiceRequestId())
                                .carServiceRequestNumber(carServiceRequestEntity.getCarServiceRequestNumber())
                                .receivedDateTime(carServiceRequestEntity.getReceivedDateTime())
                                .completedDateTime(carServiceRequestEntity.getCompletedDateTime())
                                .comment(carServiceRequestEntity.getComment())
                                .build())
                .collect(Collectors.toSet());
    }

    private Set<Invoice> getInvoices(Set<InvoiceEntity> invoiceEntitySet) {
        return invoiceEntitySet.stream().map(invoiceEntity ->
                        Invoice.builder()
                                .invoiceId(invoiceEntity.getInvoiceId())
                                .invoiceNumber(invoiceEntity.getInvoiceNumber())
                                .dateTime(invoiceEntity.getDateTime())
                                .carToSell(CarToSell.builder()
                                        .carToSellId(invoiceEntity.getCarToSell().getCarToSellId())
                                        .build())
                                .customer(Customer.builder()
                                        .customerId(invoiceEntity.getCustomer().getCustomerId())
                                        .build())
                                .salesman(Salesman.builder()
                                        .salesmanId(invoiceEntity.getSalesman().getSalesmanId())
                                        .build())
                                .build())
                .collect(Collectors.toSet());
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

}
