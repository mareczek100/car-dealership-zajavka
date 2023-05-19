package pl.mareczek100.infrastructure.data_storage;

import lombok.Value;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.database.entity.Salesman;

import java.util.List;

@Value
@Repository
public class SalesmanDataStorage {
    TrafficData trafficData;

    public List<Salesman> createSalesman() {

        return trafficData.getSalesmanList().stream()
                .map(string -> string.split(";"))
                .map(arr -> Salesman.builder()
                        .name(arr[0])
                        .surname(arr[1])
                        .pesel(arr[2])
                        .build())
                .toList();
    }

        //inny sposób
/*        List<List<String>> salesmanParametersList = new ArrayList<>();

        for (int i = 0; i < trafficData.getSalesmanList().size(); i++) {
            salesmanParametersList.add(i, Arrays.stream(trafficData.getSalesmanList().get(i)
                            .split(";")).
                    toList());
        }

        return salesmanParametersList.stream()
                .map(list -> Salesman.builder()
                        .name(list.get(0))
                        .surname(list.get(1))
                        .pesel(list.get(2))
                        .build())
                .toList();*/


}