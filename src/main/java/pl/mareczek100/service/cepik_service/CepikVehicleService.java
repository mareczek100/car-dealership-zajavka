package pl.mareczek100.service.cepik_service;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import pl.mareczek100.domain.cepik_api_domain.CepikVehicle;
import pl.mareczek100.service.cepik_dao.CepikVehicleDaoRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class CepikVehicleService {

    private final CepikVehicleDaoRepository cepikVehicleDaoRepository;

    public List<CepikVehicle> findCepikVehiclesByDate(LocalDate dateFrom, LocalDate dateTo) {
        List<CepikVehicle> cepikVehiclesByDate = cepikVehicleDaoRepository.getCepikVehiclesByDate(dateFrom, dateTo);
        if (cepikVehiclesByDate.isEmpty()) {
            throw new DataIntegrityViolationException(
                    "Cepik didn't find cars between dates: [%s] and [%s]".formatted(dateFrom, dateTo));
        }
        return cepikVehiclesByDate;
    }

    public CepikVehicle findCepikVehicle(String vehicleId, LocalDate dateFrom) {
        return cepikVehicleDaoRepository.getCepikRandomNumberVehicle(vehicleId, dateFrom);
    }

}
