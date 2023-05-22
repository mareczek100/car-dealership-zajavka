package pl.mareczek100.service;

import lombok.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mareczek100.domain.Salesman;
import pl.mareczek100.service.dao.SalesmanRepository;

import java.util.List;

@Value
@Service
public class SalesmanService {
    SalesmanRepository salesmanRepository;
    @Transactional
    public Salesman findSalesman(String pesel) {
        return salesmanRepository.findSalesman(pesel)
                .orElseThrow(() -> new RuntimeException("No such salesman [%s]!".formatted(pesel)));
    }
    @Transactional
    public List<Salesman> findAllSalesman() {
        List<Salesman> allSalesman = salesmanRepository.findAllSalesman();
        if (allSalesman.isEmpty()){
           throw  new RuntimeException("No salesman's at all!");
        }
        return allSalesman;
    }


}