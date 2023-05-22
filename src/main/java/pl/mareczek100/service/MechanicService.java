package pl.mareczek100.service;

import lombok.Value;
import org.springframework.stereotype.Service;
import pl.mareczek100.domain.Mechanic;
import pl.mareczek100.service.dao.MechanicRepository;

import java.util.List;

@Value
@Service
public class MechanicService {

    MechanicRepository mechanicRepository;

    public Mechanic findMechanic(String pesel){
        return mechanicRepository.findMechanic(pesel)
                .orElseThrow(() -> new RuntimeException("Sorry, Mechanic [%s] didn't exist!".formatted(pesel)));
    }

    public List<Mechanic> findAllMechanics() {
        List<Mechanic> allMechanicEntities = mechanicRepository.findAllMechanics();
        if (allMechanicEntities.isEmpty()){
            throw  new RuntimeException("No mechanic's at all!");
        }
        return allMechanicEntities;
    }

}
