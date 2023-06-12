package pl.mareczek100.api.dto.dtomapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.mareczek100.api.dto.CarServiceHandlingDTO;
import pl.mareczek100.domain.CarServiceHandling;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarServiceHandlingDtoMapper {


    CarServiceHandling mapFromDTO(CarServiceHandlingDTO carServiceHandlingDTO);

}
