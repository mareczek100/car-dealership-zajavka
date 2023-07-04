package pl.mareczek100.api.cepik.controller.rest;

import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.mareczek100.api.cepik.cepik_dto.CepikVehicleDTO;
import pl.mareczek100.api.cepik.cepik_dto.mapperdto.CepikVehicleMapper;
import pl.mareczek100.domain.cepik_api_domain.CepikVehicle;
import pl.mareczek100.service.cepik_service.CepikVehicleService;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cepik")
public class CepikVehicleRestController {

    private final CepikVehicleService cepikVehicleService;
    private final CepikVehicleMapper cepikVehicleMapper;
    @ApiResponse(code = 200, message = "Cepik Vehicles found by dates from-to!")
    @GetMapping("/vehicles")
    public List<CepikVehicleDTO> homePageCepikVehicleList(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo
            ) {

        return cepikVehicleService.findCepikVehiclesByDate(dateFrom, dateTo).stream()
                .map(cepikVehicleMapper::mapToDto)
                .toList();
    }

    @Operation(summary = "Get cepik vehicle by vehicle id!")
    @GetMapping("/vehicle")
    public CepikVehicleDTO homePageCepikVehicle(
            @RequestParam String cepikVehicleId,
            @RequestParam LocalDate dateFrom
            ) {
        CepikVehicle cepikVehicle = cepikVehicleService.findCepikVehicle(cepikVehicleId, dateFrom);

        return cepikVehicleMapper.mapToDto(cepikVehicle);
    }
}