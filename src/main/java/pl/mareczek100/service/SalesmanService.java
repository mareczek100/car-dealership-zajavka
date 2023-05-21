package pl.mareczek100.service;

import lombok.Value;
import org.springframework.stereotype.Service;
import pl.mareczek100.infrastructure.data_storage.SalesmanDataStorage;
import pl.mareczek100.infrastructure.database.entity.SalesmanEntity;
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

    public SalesmanEntity findSalesman(String pesel) {
        return salesmanRepository.findSalesman(pesel)
                .orElseThrow(() -> new RuntimeException("No such salesman [%s]!".formatted(pesel)));
    }

    public List<SalesmanEntity> findAllSalesman() {
        List<SalesmanEntity> allSalesmanEntity = salesmanRepository.findAllSalesman();
        if (allSalesmanEntity.isEmpty()){
           throw  new RuntimeException("No salesman's at all!");
        }
        return allSalesmanEntity;
    }


}