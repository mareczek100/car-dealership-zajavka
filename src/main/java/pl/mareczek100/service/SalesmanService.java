package pl.mareczek100.service;

import lombok.Value;
import org.springframework.stereotype.Service;
import pl.mareczek100.infrastructure.data_storage.SalesmanDataStorage;
import pl.mareczek100.infrastructure.database.entity.Salesman;
import pl.mareczek100.service.dao.SalesmanRepository;

import java.util.List;

@Value
@Service
public class SalesmanService {
    SalesmanRepository salesmanRepository;
    SalesmanDataStorage salesmanDataStorage;


    public void salesmanInit() {
        salesmanDataStorage.createSalesman()
                .forEach(salesmanRepository::salesmanInit);
    }

    public Salesman findSalesman(String pesel) {
        return salesmanRepository.findSalesman(pesel)
                .orElseThrow(() -> new RuntimeException("No such salesman [%s]!".formatted(pesel)));
    }

    public List<Salesman> findAllSalesman() {
        List<Salesman> allSalesman = salesmanRepository.findAllSalesman();
        if (allSalesman.isEmpty()){
           throw  new RuntimeException("No salesman's at all!");
        }
        return allSalesman;
    }


}