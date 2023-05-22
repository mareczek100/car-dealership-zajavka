package pl.mareczek100.service;

import lombok.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mareczek100.domain.Part;
import pl.mareczek100.service.dao.PartRepository;

import java.util.List;

@Value
@Service
public class PartService {
    PartRepository partRepository;

    @Transactional
    public Part findPart(String serialNumber){
        return partRepository.findPart(serialNumber)
                .orElseThrow(() -> new RuntimeException("Sorry, part [%s] didn't exist!".formatted(serialNumber)));
    }
    @Transactional
    public List<Part> findAllParts() {
        List<Part> allPartEntities = partRepository.findAllParts();
        if (allPartEntities.isEmpty()){
            throw  new RuntimeException("No part's at all!");
        }
        return allPartEntities;
    }

}
