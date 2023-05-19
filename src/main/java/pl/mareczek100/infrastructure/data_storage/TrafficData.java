package pl.mareczek100.infrastructure.data_storage;

import lombok.Value;
import org.springframework.stereotype.Repository;

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

    private final static Path TRAFFIC_SIMULATION = Path.of("./src/main/resources/car-dealership-traffic-simulation.md");
    private final static String INIT = "INIT";
    private final static String WHAT = "WHAT";
    private final static String SALESMAN = "SALESMAN";
    private final static String MECHANIC = "MECHANIC";
    private final static String CAR = "CAR";
    private final static String SERVICE = "SERVICE";
    private final static String PART = "PART";
    private final static String CUSTOMER = "CUSTOMER";
    private final static String INVOICE = "INVOICE";
    private final static String BUY_FIRST_TIME = "BUY_FIRST_TIME";
    private final static String BUY_AGAIN = "BUY_AGAIN";
    private final static String SERVICE_REQUEST = "SERVICE_REQUEST";
    private final static String DO_THE_SERVICE = "DO_THE_SERVICE";

    List<String> salesmanList = createSalesmanList();
    List<String> mechanicList = createMechanicList();
    List<String> carList = createCarList();
    List<String> serviceList = createServiceList();
    List<String> partList = createPartList();
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
            List<String> pesel = Files.readAllLines(TRAFFIC_SIMULATION).stream()
                    .filter(line -> !line.contains("#"))
                    .filter(line -> line.contains(DO_THE_SERVICE) && line.contains(MECHANIC))
                    .flatMap(line -> Arrays.stream(line.split(" -> "))
                            .skip(2)
                            .filter(string -> !string.equalsIgnoreCase(CAR))
                            .filter(string -> !string.equalsIgnoreCase(WHAT))
                            .limit(1))
                    .toList();

            List<String> vin = Files.readAllLines(TRAFFIC_SIMULATION).stream()
                    .filter(line -> !line.contains("#"))
                    .filter(line -> line.contains(DO_THE_SERVICE) && line.contains(MECHANIC))
                    .flatMap(line -> Arrays.stream(line.split(" -> "))
                            .skip(4)
                            .limit(1))
                    .toList();

            List <String> serviceParameters = Files.readAllLines(TRAFFIC_SIMULATION).stream()
                    .filter(line -> !line.contains("#"))
                    .filter(line -> line.contains(DO_THE_SERVICE) && line.contains(MECHANIC))
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
            return Files.readAllLines(TRAFFIC_SIMULATION).stream()
                    .filter(line -> !line.contains("#"))
                    .filter(line -> line.contains(SERVICE_REQUEST) && line.contains(CUSTOMER))
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
            List<String> outerCarAndRequestCommentList = Files.readAllLines(TRAFFIC_SIMULATION).stream()
                    .filter(line -> !line.contains("#"))
                    .filter(line -> line.contains(SERVICE_REQUEST) && line.contains(CUSTOMER))
                    .skip(2)
                    .flatMap(line -> Arrays.stream(line.split("->"))
                            .skip(4)
                            .map(String::strip)
                            .filter(string -> !string.equalsIgnoreCase(WHAT)))
                    .toList();

            List<String> customerEmailTempList = Files.readAllLines(TRAFFIC_SIMULATION).stream()
                    .filter(line -> !line.contains("#"))
                    .filter(line -> line.contains(SERVICE_REQUEST) && line.contains(CUSTOMER))
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
            List<String> carVinAndRequestCommentList = Files.readAllLines(TRAFFIC_SIMULATION).stream()
                    .filter(line -> !line.contains("#"))
                    .filter(line -> line.contains(SERVICE_REQUEST) && line.contains(CUSTOMER))
                    .map(String::trim)
                    .flatMap(line -> Arrays.stream(line.split("->"))
                            .skip(4)
                            .map(String::trim)
                            .filter(string -> !string.equalsIgnoreCase(WHAT)))
                    .limit(4)
                    .toList();

            List<String> customerEmailTempList = Files.readAllLines(TRAFFIC_SIMULATION).stream()
                    .filter(line -> !line.contains("#"))
                    .filter(line -> line.contains(SERVICE_REQUEST) && line.contains(CUSTOMER))
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
            List<String> customerEmailTempList = Files.readAllLines(TRAFFIC_SIMULATION).stream()
                    .filter(line -> !line.contains("#"))
                    .filter(line -> line.contains(BUY_AGAIN) && line.contains(CUSTOMER))
                    .flatMap(line -> Arrays.stream(line.split("->")))
                    .map(String::strip)
                    .filter(word -> word.contains("@"))
                    .toList();

            List<String> carVinAndSalesmanPeselTempList = Files.readAllLines(TRAFFIC_SIMULATION).stream()
                    .filter(line -> !line.contains("#"))
                    .filter(line -> line.contains(BUY_AGAIN) && line.contains(CUSTOMER))
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
            List<String> carVinAndSalesmanPeselTempList = Files.readAllLines(TRAFFIC_SIMULATION).stream()
                    .filter(line -> !line.contains("#"))
                    .filter(line -> line.contains(BUY_FIRST_TIME) && line.contains(CUSTOMER))
                    .flatMap(line -> Arrays.stream(line.split("->"))
                            .skip(4)
                            .map(String::strip)
                            .filter(string -> !SALESMAN.equalsIgnoreCase(string)))
                    .toList();

            List<String> customerEmailTempList = Files.readAllLines(TRAFFIC_SIMULATION).stream()
                    .filter(line -> !line.contains("#"))
                    .filter(line -> line.contains(BUY_FIRST_TIME) && line.contains(CUSTOMER))
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
            return Files.readAllLines(TRAFFIC_SIMULATION).stream()
                    .filter(line -> !line.contains("#"))
                    .filter(line -> line.contains(BUY_FIRST_TIME) && line.contains(CUSTOMER))
                    .map(line -> line.substring(
                            line.indexOf(">", line.indexOf(">") + 1) + 1, line.lastIndexOf("-> CAR")))
                    .map(String::strip)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> createServiceList() {
        try {
            return Files.readAllLines(TRAFFIC_SIMULATION).stream()
                    .filter(line -> !line.contains("#"))
                    .filter(line -> line.contains(INIT) && line.contains(SERVICE))
                    .map(line -> line.substring(line.lastIndexOf(">") + 1))
                    .map(String::strip)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> createCarList() {
        try {
            return Files.readAllLines(TRAFFIC_SIMULATION).stream()
                    .filter(line -> !line.contains("#"))
                    .filter(line -> line.contains(INIT) && line.contains(CAR))
                    .map(line -> line.substring(line.lastIndexOf(">") + 1))
                    .map(String::strip)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> createPartList() {
        try {
            return Files.readAllLines(TRAFFIC_SIMULATION).stream()
                    .filter(line -> !line.contains("#"))
                    .filter(line -> line.contains(INIT) && line.contains(PART))
                    .map(line -> line.substring(line.lastIndexOf(">") + 1))
                    .map(String::strip)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> createMechanicList() {
        try {
            return Files.readAllLines(TRAFFIC_SIMULATION).stream()
                    .filter(line -> !line.stripTrailing().contains("#"))
                    .filter(line -> line.contains(INIT) && line.contains(MECHANIC))
                    .map(line -> line.substring(line.lastIndexOf(">") + 1))
                    .map(String::strip)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private List<String> createSalesmanList() {

        try {
            return Files.readAllLines(TRAFFIC_SIMULATION).stream()
                    .filter(line -> !line.contains("#"))
                    .filter(line -> line.contains(INIT) && line.contains(SALESMAN))
                    .map(line -> line.substring(line.lastIndexOf(">") + 1))
                    .map(String::strip)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
