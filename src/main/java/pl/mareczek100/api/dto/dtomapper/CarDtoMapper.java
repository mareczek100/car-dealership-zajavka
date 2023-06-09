package pl.mareczek100.api.dto.dtomapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.mareczek100.api.dto.CarDTO;
import pl.mareczek100.api.dto.CarServiceDTO;
import pl.mareczek100.domain.CarToSell;
import pl.mareczek100.domain.CarToSellTempStorage;
import pl.mareczek100.domain.CarToService;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarDtoMapper {

    CarDTO mapToDTO(CarToSell car);
    CarServiceDTO mapToCarServiceDTO(CarToService car);
    CarToService mapFromDTO(CarDTO car);
    CarDTO mapToDTO(CarToSellTempStorage car);

}
