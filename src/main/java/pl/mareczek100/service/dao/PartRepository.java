package pl.mareczek100.service.dao;

import pl.mareczek100.infrastructure.database.entity.Part;

import java.util.List;
import java.util.Optional;


public interface PartRepository {


    void partInit(Part part);

    Optional<Part> findPart(String serialNumber);

    List<Part> findAllParts();

}
