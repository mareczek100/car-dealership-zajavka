package pl.mareczek100.infrastructure.database.entityMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.mareczek100.domain.*;
import pl.mareczek100.infrastructure.database.entity.*;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarServiceRequestEntityMapper {

    //    @Mapping(target = "carServiceHandling.carServiceRequest", ignore = true)
//    @Mapping(target = "carServicePart.carServiceRequest", ignore = true)
//    @Mapping(target = "customer.address", ignore = true)
//    @Mapping(target = "customer.carServiceRequests", ignore = true)
//    @Mapping(target = "customer.invoices", ignore = true)
//    @Mapping(target = "carToService.carServiceRequests", ignore = true)

//    @Mapping(target = "carServiceHandling", ignore = true)
//    @Mapping(target = "carServicePart", ignore = true)
    default CarServiceRequest mapFromEntity(CarServiceRequestEntity carServiceRequestEntity)
    {
        return CarServiceRequest.builder()
                .carServiceRequestId(carServiceRequestEntity.getCarServiceRequestId())
                .carServiceRequestNumber(carServiceRequestEntity.getCarServiceRequestNumber())
                .receivedDateTime(carServiceRequestEntity.getReceivedDateTime())
                .completedDateTime(carServiceRequestEntity.getCompletedDateTime())
                .comment(carServiceRequestEntity.getComment())
                .customer(getCustomer(carServiceRequestEntity.getCustomer()))
                .carToService(getCarToService(carServiceRequestEntity.getCarToService()))
                .carServiceHandling(getCarServiceHandling(carServiceRequestEntity.getCarServiceHandling()))
                .carServicePart(getCarServiceParts(carServiceRequestEntity.getCarServicePart()))
                .build();
    }

    @Mapping(target = "carServiceHandling", ignore = true)
    @Mapping(target = "carServicePart", ignore = true)
//    @Mapping(target = "customer.address", ignore = true)
//    @Mapping(target = "customer.carServiceRequests", ignore = true)
//    @Mapping(target = "customer.invoices", ignore = true)
//    @Mapping(target = "carToService.carServiceRequests", ignore = true)
    CarServiceRequestEntity mapToEntity(CarServiceRequest carServiceRequest);

    private Customer getCustomer(CustomerEntity customerEntity) {
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

    private CarToService getCarToService(CarToServiceEntity carToServiceEntity) {
        return CarToService.builder()
                .carToServiceId(carToServiceEntity.getCarToServiceId())
                .vin(carToServiceEntity.getVin())
                .brand(carToServiceEntity.getBrand())
                .model(carToServiceEntity.getModel())
                .year(carToServiceEntity.getYear())
                .build();
    }

    private Set<CarServiceHandling> getCarServiceHandling(Set<CarServiceHandlingEntity> carServiceHandlingEntitySet) {
        return carServiceHandlingEntitySet.stream()
                .map(carServiceHandlingEntity ->
                        CarServiceHandling.builder()
                                .carServiceHandlingId(carServiceHandlingEntity.getCarServiceHandlingId())
                                .hours(carServiceHandlingEntity.getHours())
                                .comment(carServiceHandlingEntity.getComment())
                                .mechanic(getMechanic(carServiceHandlingEntity.getMechanic()))
                                .service(getService(carServiceHandlingEntity.getService()))
                                .build())
                .collect(Collectors.toSet());
    }

    private Mechanic getMechanic(MechanicEntity mechanicEntity) {
        return Mechanic.builder()
                .mechanicId(mechanicEntity.getMechanicId())
                .name(mechanicEntity.getName())
                .surname(mechanicEntity.getSurname())
                .pesel(mechanicEntity.getPesel())
                .build();
    }

    private Service getService(ServiceEntity serviceEntity) {
        return Service.builder()
                .serviceId(serviceEntity.getServiceId())
                .serviceCode(serviceEntity.getServiceCode())
                .description(serviceEntity.getDescription())
                .price(serviceEntity.getPrice())
                .build();
    }

    private Set<CarServiceParts> getCarServiceParts(Set<CarServicePartsEntity> carServicePartsEntitySet) {
        return carServicePartsEntitySet.stream()
                .map(carServicePartsEntity ->
                        CarServiceParts.builder()
                                .carServicePartsId(carServicePartsEntity.getCarServicePartsId())
                                .quantity(carServicePartsEntity.getQuantity())
                                .part(getPart(carServicePartsEntity.getPart()))
                                .build())
                .collect(Collectors.toSet());
    }

    private Part getPart(PartEntity partEntity) {
        if (Objects.isNull(partEntity)) {
            return null;
        }
        return Part.builder()
                .partId(partEntity.getPartId())
                .serialNumber(partEntity.getSerialNumber())
                .description(partEntity.getDescription())
                .price(partEntity.getPrice())
                .build();
    }


}
