package pl.mareczek100.service;

import lombok.Value;
import org.springframework.stereotype.Service;
import pl.mareczek100.infrastructure.data_storage.PartDataStorage;
import pl.mareczek100.infrastructure.database.entity.PartEntity;
import pl.mareczek100.service.dao.PartRepository;

import java.util.List;

@Value
@Service
public class PartService {
    PartDataStorage partDataStorage;
    PartRepository partRepository;


    public void partInit() {
        partDataStorage.createPart()
                .forEach(partRepository::partInit);
    }

    public PartEntity findPart(String serialNumber){
        return partRepository.findPart(serialNumber)
                .orElseThrow(() -> new RuntimeException("Sorry, part [%s] didn't exist!".formatted(serialNumber)));
    }

    public List<PartEntity> findAllParts() {
        List<PartEntity> allPartEntities = partRepository.findAllParts();
        if (allPartEntities.isEmpty()){
            throw  new RuntimeException("No part's at all!");
        }
        return allPartEntities;
    }

}
