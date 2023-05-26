package pl.mareczek100.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mareczek100.domain.Mechanic;
import pl.mareczek100.service.dao.MechanicRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class MechanicService {

    private final MechanicRepository mechanicRepository;
    @Transactional
    public Mechanic findMechanic(String pesel){
        return mechanicRepository.findMechanic(pesel)
                .orElseThrow(() -> new RuntimeException("Sorry, Mechanic [%s] didn't exist!".formatted(pesel)));
    }
    @Transactional
    public List<Mechanic> findAllMechanics() {
        List<Mechanic> allMechanicEntities = mechanicRepository.findAllMechanics();
        if (allMechanicEntities.isEmpty()){
            throw  new RuntimeException("No mechanic's at all!");
        }
        return allMechanicEntities;
    }

}
