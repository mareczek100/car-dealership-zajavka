package pl.mareczek100.api.cepik.cepik_dto.mapperdto;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.mareczek100.api.cepik.cepik_dto.CepikVehicleDTO;
import pl.mareczek100.domain.cepik_api_domain.CepikVehicle;
import pl.mareczek100.infrastructure.cepik.model.VehicleDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CepikVehicleMapper {

    CepikVehicleDTO mapToDto(CepikVehicle cepikVehicle);

    default CepikVehicle mapFromCepikDto(VehicleDto vehicleDto) {
        return CepikVehicle.builder()
                .brand(vehicleDto.getMarka())
                .model(vehicleDto.getModel())
                .type(vehicleDto.getTyp())
                .engineCapacity(vehicleDto.getPojemnoscSkokowaSilnika())
                .weight(vehicleDto.getMasaWlasna())
                .fuel(vehicleDto.getRodzajPaliwa())
                .build();
    }

}
