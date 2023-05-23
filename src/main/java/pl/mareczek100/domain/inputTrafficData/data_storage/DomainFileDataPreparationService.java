package pl.mareczek100.domain.inputTrafficData.data_storage;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.mareczek100.domain.CarServiceRequest;
import pl.mareczek100.domain.Customer;
import pl.mareczek100.domain.inputTrafficData.CarPurchaseInputData;
import pl.mareczek100.domain.inputTrafficData.CarServiceProcessInputData;
import pl.mareczek100.domain.inputTrafficData.CarServiceRequestInputData;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class DomainFileDataPreparationService {

    private final static String FINISHED = "FINISHED";
    CarServiceProcessDataStorage carServiceProcessDataStorage;
    CarServiceRequestDataStorage carServiceRequestDataStorage;
    PurchaseDataStorage purchaseDataStorage;

    public List<CarPurchaseInputData> prepareFirstTimePurchaseData() {
        List<CarPurchaseInputData> carPurchaseInputData = new ArrayList<>();
        List<String[]> vinAndPesel = purchaseDataStorage.vinAndPeselFirstTime();
        List<Customer> customerWithAddress = purchaseDataStorage.customerWithAddress();
        for (int i = 0; i < customerWithAddress.size(); i++) {
            carPurchaseInputData.add(
                    CarPurchaseInputData.builder()
                            .customerName(customerWithAddress.get(i).getName())
                            .customerSurname(customerWithAddress.get(i).getSurname())
                            .customerPhone(customerWithAddress.get(i).getPhone())
                            .customerEmail(customerWithAddress.get(i).getEmail())
                            .customerAddressCountry(customerWithAddress.get(i).getAddress().getCountry())
                            .customerAddressCity(customerWithAddress.get(i).getAddress().getCity())
                            .customerAddressPostalCode(customerWithAddress.get(i).getAddress().getPostalCode())
                            .customerAddressStreet(customerWithAddress.get(i).getAddress().getStreet())
                            .customerAddressBuildingFlatNumber(customerWithAddress.get(i).getAddress().getBuildingFlatNumber())
                            .carVin(vinAndPesel.get(i)[1])
                            .salesmanPesel(vinAndPesel.get(i)[2])
                            .build());
        }
        return carPurchaseInputData;
    }

    public List<CarPurchaseInputData> prepareNextTimePurchaseData() {
        List<CarPurchaseInputData> carPurchaseInputData = new ArrayList<>();
        String[] emailVinAndPesel = purchaseDataStorage.emailVinAndPeselAgain();
        CarPurchaseInputData.builder()
                .customerEmail(emailVinAndPesel[0])
                .carVin(emailVinAndPesel[1])
                .salesmanPesel(emailVinAndPesel[2])
                .build();
        return carPurchaseInputData;
    }

    public List<CarServiceRequestInputData> prepareInnerServiceRequestInputData() {
        List<CarServiceRequestInputData> carServiceRequestInputData = new ArrayList<>();
        List<CarServiceRequest> innerCarServiceRequest = carServiceRequestDataStorage.createInnerCarServiceRequest();

        for (CarServiceRequest carServiceRequest : innerCarServiceRequest) {
            carServiceRequestInputData.add(
                    CarServiceRequestInputData.builder()
                            .customer(carServiceRequest.getCustomer())
                            .carToService(carServiceRequest.getCarToService())
                            .customerComment(carServiceRequest.getCustomerComment())
                            .build()
            );
        }
        return carServiceRequestInputData;
    }

    public List<CarServiceRequestInputData> prepareOutsideServiceRequestInputData() {
        List<CarServiceRequestInputData> carServiceRequestInputData = new ArrayList<>();
        List<CarServiceRequest> outerCarServiceRequest = carServiceRequestDataStorage.createOuterCarServiceRequest();

        for (CarServiceRequest carServiceRequest : outerCarServiceRequest) {
            carServiceRequestInputData.add(
                    CarServiceRequestInputData.builder()
                            .customer(carServiceRequest.getCustomer())
                            .carToService(carServiceRequest.getCarToService())
                            .customerComment(carServiceRequest.getCustomerComment())
                            .build()
            );
        }
        return carServiceRequestInputData;
    }

    public List<CarServiceProcessInputData> prepareCarServiceProcessInputData() {
        List<CarServiceProcessInputData> carServiceProcessInputData = new ArrayList<>();
        List<String[]> carServiceHandling = carServiceProcessDataStorage.createCarServiceProcessingInputData();

        for (String[] serviceHandling : carServiceHandling) {
            carServiceProcessInputData.add(
                    CarServiceProcessInputData.builder()
                            .mechanicPesel(serviceHandling[0])
                            .carVin(serviceHandling[1])
                            .partSerialNumber(serviceHandling[2])
                            .partQuantity(Short.valueOf(serviceHandling[3]))
                            .serviceCode(serviceHandling[4])
                            .serviceHandlingHours(Short.valueOf(serviceHandling[5]))
                            .mechanicComment(serviceHandling[6])
                            .finished(finishedOrNot(serviceHandling[7]))
                            .build()
            );

        }
        return carServiceProcessInputData;
    }

    private Boolean finishedOrNot(String done) {
        return done.equalsIgnoreCase(FINISHED);
    }


}


