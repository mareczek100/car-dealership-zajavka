package pl.mareczek100.domain.inputTrafficData.data_storage;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.mareczek100.domain.CarServiceRequest;
import pl.mareczek100.domain.inputTrafficData.CarPurchaseInputData;
import pl.mareczek100.domain.inputTrafficData.CarRepairProcessData;
import pl.mareczek100.domain.inputTrafficData.CustomerCarRepairRequestInputData;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class DomainFileDataService {

    private final static String FINISHED = "FINISHED";
    private final CarServiceProcessDataStorage carServiceProcessDataStorage;
    private final CarServiceRequestDataStorage carServiceRequestDataStorage;
    private final PurchaseDataStorage purchaseDataStorage;



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

    public List<CustomerCarRepairRequestInputData> prepareInnerServiceRequestInputData() {
        List<CustomerCarRepairRequestInputData> customerCarRepairRequestInputData = new ArrayList<>();
        List<CarServiceRequest> innerCarServiceRequest = carServiceRequestDataStorage.createInnerCarServiceRequest();

        for (CarServiceRequest carServiceRequest : innerCarServiceRequest) {
            customerCarRepairRequestInputData.add(
                    CustomerCarRepairRequestInputData.builder()
                            .requestedCustomer(carServiceRequest.getCustomer())
                            .customerCarToRepair(carServiceRequest.getCarToService())
                            .customerComment(carServiceRequest.getComment())
                            .build()
            );
        }
        return customerCarRepairRequestInputData;
    }

    public List<CustomerCarRepairRequestInputData> prepareOutsideServiceRequestInputData() {
        List<CustomerCarRepairRequestInputData> customerCarRepairRequestInputData = new ArrayList<>();
        List<CarServiceRequest> outerCarServiceRequest = carServiceRequestDataStorage.createOuterCarServiceRequest();

        for (CarServiceRequest carServiceRequest : outerCarServiceRequest) {
            customerCarRepairRequestInputData.add(
                    CustomerCarRepairRequestInputData.builder()
                            .requestedCustomer(carServiceRequest.getCustomer())
                            .customerCarToRepair(carServiceRequest.getCarToService())
                            .customerComment(carServiceRequest.getComment())
                            .build()
            );
        }
        return customerCarRepairRequestInputData;
    }

    public List<CarRepairProcessData> carServiceProcessData() {
        List<CarRepairProcessData> carRepairProcessData = new ArrayList<>();
        List<String[]> carServiceHandling = carServiceProcessDataStorage.createCarServiceProcessingInputData();

        for (String[] serviceHandling : carServiceHandling) {
            carRepairProcessData.add(
                    CarRepairProcessData.builder()
                            .mechanicPesel(serviceHandling[0])
                            .carVin(serviceHandling[1])
                            .partSerialNumber(partNumber(serviceHandling[2]))
                            .partQuantity(getQuantity(serviceHandling[3]))
                            .serviceCode(serviceHandling[4])
                            .serviceHandlingHours(Short.valueOf(serviceHandling[5]))
                            .mechanicComment(serviceHandling[6])
                            .finished(finishedOrNot(serviceHandling[7]))
                            .build()
            );
        }
        return carRepairProcessData;
    }

    private Short getQuantity(String quantity) {
        if (quantity.isBlank()) {
            return null;
        }
        return Short.valueOf(quantity);
    }
    private String partNumber(String number) {
        if (number.isBlank()) {
            return null;
        }
        return number;
    }

    private Boolean finishedOrNot(String done) {
        return done.equalsIgnoreCase(FINISHED);
    }


}


