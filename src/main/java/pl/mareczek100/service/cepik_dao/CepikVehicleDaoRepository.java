package pl.mareczek100.service.cepik_dao;

import pl.mareczek100.domain.cepik_api_domain.CepikVehicle;

import java.time.LocalDate;
import java.util.List;

public interface CepikVehicleDaoRepository {

    List<CepikVehicle> getCepikVehiclesByDate(LocalDate dateFrom, LocalDate dateTo);
    CepikVehicle getCepikRandomNumberVehicle(String vehicleId, LocalDate dateFrom);

}
