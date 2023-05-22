package pl.mareczek100.domain.inputTrafficData.data_storage;

import lombok.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Value
@Repository
class TrafficData {

    private final static Path TRAFFIC;

    static {
        try {
            TRAFFIC = ResourceUtils.getFile("classpath:car-dealership-traffic-simulation.md").toPath();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private final static String WHAT = "WHAT";
    private final static String SALESMAN = "SALESMAN";
    private final static String CAR = "CAR";
    private final static String BUY_FIRST_TIME = "BUY_FIRST_TIME";
    private final static String BUY_AGAIN = "BUY_AGAIN";
    private final static String SERVICE_REQUEST = "SERVICE_REQUEST";
    private final static String DO_THE_SERVICE = "DO_THE_SERVICE";

    List<String> customerBuyingList = createCustomerBuyingList();
    List<String> invoiceList = createInvoiceCarBuyingParameterList();
    List<String> invoiceListAgain = createInvoiceCarBuyingAgainList();
    List<String> carFromDealerServiceRequestList = carFromDealerServiceRequestList();
    List<String> carOuterServiceRequestList = createOuterCarServiceRequestList();
    List<String> customerOuterList = createCustomerOuterList();
    List<String> carServiceHandlingList = createCarServiceHandlingList();


    private List<String> createCarServiceHandlingList() {

        List<String> resultList = new ArrayList<>();

        try {
            List<String> pesel = Files.readAllLines(TRAFFIC).stream()
                    .filter(line -> !line.contains("#"))
                    .filter(line -> line.contains(DO_THE_SERVICE))
                    .flatMap(line -> Arrays.stream(line.split(" -> "))
                            .skip(2)
                            .filter(string -> !string.equalsIgnoreCase(CAR))
                            .filter(string -> !string.equalsIgnoreCase(WHAT))
                            .limit(1))
                    .toList();

            List<String> vin = Files.readAllLines(TRAFFIC).stream()
                    .filter(line -> !line.contains("#"))
                    .filter(line -> line.contains(DO_THE_SERVICE))
                    .flatMap(line -> Arrays.stream(line.split(" -> "))
                            .skip(4)
                            .limit(1))
                    .toList();

            List <String> serviceParameters = Files.readAllLines(TRAFFIC).stream()
                    .filter(line -> !line.contains("#"))
                    .filter(line -> line.contains(DO_THE_SERVICE))
                    .flatMap(line -> Arrays.stream(line.split(" -> "))
                            .skip(2)
                            .filter(string -> !string.equalsIgnoreCase(CAR))
                            .filter(string -> !string.equalsIgnoreCase(WHAT))
                            .skip(2))
                    .toList();

            for (int i = 0; i < serviceParameters.size(); i++) {
                resultList.add(pesel.get(i).concat(";").concat(vin.get(i)).concat(";")
                        .concat(serviceParameters.get(i)));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    private List<String> createCustomerOuterList() {
        try {
            return Files.readAllLines(TRAFFIC).stream()
                    .filter(line -> !line.contains("#"))
                    .filter(line -> line.contains(SERVICE_REQUEST))
                    .skip(2)
                    .map(line -> line.substring(
                            line.indexOf(">", line.indexOf(">") + 1) + 1, line.lastIndexOf("-> CAR")))
                    .map(String::strip)
                    .toList();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private List<String> createOuterCarServiceRequestList() {
        List<String> resultList;

        try {
            List<String> outerCarAndRequestCommentList = Files.readAllLines(TRAFFIC).stream()
                    .filter(line -> !line.contains("#"))
                    .filter(line -> line.contains(SERVICE_REQUEST))
                    .skip(2)
                    .flatMap(line -> Arrays.stream(line.split("->"))
                            .skip(4)
                            .map(String::strip)
                            .filter(string -> !string.equalsIgnoreCase(WHAT)))
                    .toList();

            List<String> customerEmailTempList = Files.readAllLines(TRAFFIC).stream()
                    .filter(line -> !line.contains("#"))
                    .filter(line -> line.contains(SERVICE_REQUEST))
                    .skip(2)
                    .flatMap(line -> Arrays.stream(line.split(";")))
                    .filter(word -> word.contains("@"))
                    .map(String::strip)
                    .toList();

            resultList = IntStream.iterate(0, i -> i + 2)
                    .boxed()
                    .limit(customerEmailTempList.size())
                    .map(counter -> customerEmailTempList.get(counter / 2).concat(";")
                            .concat(outerCarAndRequestCommentList.get(counter)).concat(";")
                            .concat(outerCarAndRequestCommentList.get(counter + 1)))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    private List<String> carFromDealerServiceRequestList() {
        List<String> resultList;

        try {
            List<String> carVinAndRequestCommentList = Files.readAllLines(TRAFFIC).stream()
                    .filter(line -> !line.contains("#"))
                    .filter(line -> line.contains(SERVICE_REQUEST))
                    .map(String::trim)
                    .flatMap(line -> Arrays.stream(line.split("->"))
                            .skip(4)
                            .map(String::trim)
                            .filter(string -> !string.equalsIgnoreCase(WHAT)))
                    .limit(4)
                    .toList();

            List<String> customerEmailTempList = Files.readAllLines(TRAFFIC).stream()
                    .filter(line -> !line.contains("#"))
                    .filter(line -> line.contains(SERVICE_REQUEST))
                    .limit(2)
                    .flatMap(line -> Arrays.stream(line.split("->")))
                    .map(String::strip)
                    .filter(word -> word.contains("@"))
                    .toList();

            resultList = IntStream.iterate(0, i -> i + 2)
                    .boxed()
                    .limit(customerEmailTempList.size())
                    .map(counter -> customerEmailTempList.get(counter / 2).concat(";")
                            .concat(carVinAndRequestCommentList.get(counter)).concat(";")
                            .concat(carVinAndRequestCommentList.get(counter + 1)))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    private List<String> createInvoiceCarBuyingAgainList() {
        List<String> resultList;
        try {
            List<String> customerEmailTempList = Files.readAllLines(TRAFFIC).stream()
                    .filter(line -> !line.contains("#"))
                    .filter(line -> line.contains(BUY_AGAIN))
                    .flatMap(line -> Arrays.stream(line.split("->")))
                    .map(String::strip)
                    .filter(word -> word.contains("@"))
                    .toList();

            List<String> carVinAndSalesmanPeselTempList = Files.readAllLines(TRAFFIC).stream()
                    .filter(line -> !line.contains("#"))
                    .filter(line -> line.contains(BUY_AGAIN))
                    .map(String::strip)
                    .flatMap(line -> Arrays.stream(line.split("->"))
                            .skip(4)
                            .map(String::strip)
                            .filter(string -> !string.equalsIgnoreCase(SALESMAN)))
                    .toList();

            resultList = IntStream.iterate(0, i -> i + 2)
                    .boxed()
                    .limit(customerEmailTempList.size())
                    .map(counter -> customerEmailTempList.get(counter / 2).concat(";")
                            .concat(carVinAndSalesmanPeselTempList.get(counter)).concat(";")
                            .concat(carVinAndSalesmanPeselTempList.get(counter + 1)))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    private List<String> createInvoiceCarBuyingParameterList() {

        List<String> resultList;

        try {
            List<String> carVinAndSalesmanPeselTempList = Files.readAllLines(TRAFFIC).stream()
                    .filter(line -> !line.contains("#"))
                    .filter(line -> line.contains(BUY_FIRST_TIME))
                    .flatMap(line -> Arrays.stream(line.split("->"))
                            .skip(4)
                            .map(String::strip)
                            .filter(string -> !SALESMAN.equalsIgnoreCase(string)))
                    .toList();

            List<String> customerEmailTempList = Files.readAllLines(TRAFFIC).stream()
                    .filter(line -> !line.contains("#"))
                    .filter(line -> line.contains(BUY_FIRST_TIME))
                    .flatMap(line -> Arrays.stream(line.split(";")))
                    .filter(word -> word.contains("@"))
                    .map(String::strip)
                    .toList();

            resultList = IntStream.iterate(0, i -> i + 2)
                    .boxed()
                    .limit(customerEmailTempList.size())
                    .map(counter -> customerEmailTempList.get(counter / 2).concat(";")
                            .concat(carVinAndSalesmanPeselTempList.get(counter)).concat(";")
                            .concat(carVinAndSalesmanPeselTempList.get(counter + 1)))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    private List<String> createCustomerBuyingList() {
        try {
            return Files.readAllLines(TRAFFIC).stream()
                    .filter(line -> !line.contains("#"))
                    .filter(line -> line.contains(BUY_FIRST_TIME))
                    .map(line -> line.substring(
                            line.indexOf(">", line.indexOf(">") + 1) + 1, line.lastIndexOf("-> CAR")))
                    .map(String::strip)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}