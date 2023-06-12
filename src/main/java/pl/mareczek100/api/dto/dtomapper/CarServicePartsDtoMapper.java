package pl.mareczek100.api.dto.dtomapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.mareczek100.api.dto.CarServicePartsDTO;
import pl.mareczek100.domain.CarServiceParts;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarServicePartsDtoMapper {

    CarServicePartsDTO mapToDTO(CarServiceParts carServiceParts);
    CarServiceParts mapFromDTO(CarServicePartsDTO carServicePartsDTO);

}
