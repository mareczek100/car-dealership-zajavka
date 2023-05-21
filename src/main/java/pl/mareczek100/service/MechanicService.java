package pl.mareczek100.service;

import lombok.Value;
import org.springframework.stereotype.Service;
import pl.mareczek100.infrastructure.data_storage.MechanicDataStorage;
import pl.mareczek100.infrastructure.database.entity.MechanicEntity;
import pl.mareczek100.service.dao.MechanicRepository;

import java.util.List;

@Value
@Service
public class MechanicService {

    MechanicRepository mechanicRepository;
    MechanicDataStorage mechanicDataStorage;


    public void mechanicInit() {
        mechanicDataStorage.createMechanic()
                .forEach(mechanicRepository::mechanicInit);
    }

    public MechanicEntity findMechanic(String pesel){
        return mechanicRepository.findMechanic(pesel)
                .orElseThrow(() -> new RuntimeException("Sorry, Mechanic [%s] didn't exist!".formatted(pesel)));
    }

    public List<MechanicEntity> findAllMechanics() {
        List<MechanicEntity> allMechanicEntities = mechanicRepository.findAllMechanics();
        if (allMechanicEntities.isEmpty()){
            throw  new RuntimeException("No mechanic's at all!");
        }
        return allMechanicEntities;
    }

}
