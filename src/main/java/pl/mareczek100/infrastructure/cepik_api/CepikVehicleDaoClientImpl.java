package pl.mareczek100.infrastructure.cepik_api;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.webjars.NotFoundException;
import pl.mareczek100.api.cepik.cepik_dto.mapperdto.CepikVehicleMapper;
import pl.mareczek100.domain.cepik_api_domain.CepikVehicle;
import pl.mareczek100.infrastructure.cepik.api.PojazdyApi;
import pl.mareczek100.infrastructure.cepik.api.SownikiApi;
import pl.mareczek100.infrastructure.cepik.model.ApiAttributesDtoVehicle;
import pl.mareczek100.infrastructure.cepik.model.DictionaryDto;
import pl.mareczek100.infrastructure.cepik.model.JsonApiForListVehicle;
import pl.mareczek100.infrastructure.cepik.model.VehicleDto;
import pl.mareczek100.service.cepik_dao.CepikVehicleDaoRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CepikVehicleDaoClientImpl implements CepikVehicleDaoRepository {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final String WOJEWODZTWA_DICTIONARY = "wojewodztwa";
    private static final String WOJEWODZTWO_ZACHODNIOPOMORSKIE = "ZACHODNIOPOMORSKIE";

    private final CepikVehicleMapper cepikVehicleMapper;
    private final PojazdyApi pojazdyApi;
    private final SownikiApi slownikiApi;

    @Override
    public List<CepikVehicle> getCepikVehiclesByDate(LocalDate dateFrom, LocalDate dateTo) {
        List<VehicleDto> vehicleDtoList = getPojazdy(dateFrom, dateTo, getDictionaryWojewodztwoZachPomFilter());

        return vehicleDtoList.stream()
                .map(cepikVehicleMapper::mapFromCepikDto)
                .toList();
    }

    @Override
    public CepikVehicle getCepikRandomNumberVehicle(String vehicleId, LocalDate dateFrom) {
        var listaPojazdowJson = pojazdyApi.getListaPojazdow(
                        getDictionaryWojewodztwoZachPomFilter(),
                        dateFrom.format(DATE_FORMATTER),
                        null, "1", false, true, null, "100", "1", null)
                .block();

        String attributesDtoVehicleId = Optional.ofNullable(listaPojazdowJson.getData()).stream()
                .flatMap(Collection::stream)
                .skip(Long.parseLong(vehicleId) - 1)
                .map(apiAttributesDtoVehicle -> Objects.requireNonNull(apiAttributesDtoVehicle.getId()))
                .findFirst().orElseThrow(
                        () -> new DataIntegrityViolationException(
                                "Cepik didn't find car with id: [%s]".formatted(vehicleId)
                        ));

        var cepikVehicle = Optional.ofNullable(
                pojazdyApi.getPojazd(attributesDtoVehicleId, null).block()
                        .getData().getAttributes()).orElseThrow(
                () -> new DataIntegrityViolationException(
                        "Cepik didn't find car with id: [%s]".formatted(vehicleId)
                ));

        return cepikVehicleMapper.mapFromCepikDto(cepikVehicle);
    }

    private List<VehicleDto> getPojazdy(LocalDate dateFrom, LocalDate dateTo, String dictionaryWojFilter) {
        JsonApiForListVehicle jsonApiForListVehicle = Optional.ofNullable(pojazdyApi.getListaPojazdow(
                dictionaryWojFilter,
                dateFrom.format(DATE_FORMATTER), dateTo.format(DATE_FORMATTER), "2",
                true, true,
                null,
                "50", "1", null
        ).block()).orElseThrow(
                () -> new DataIntegrityViolationException(
                        "Cepik didn't find cars between dates [%s] and [%s]".formatted(dateFrom, dateTo)
                ));

        return jsonApiForListVehicle.getData().stream()
                .map(ApiAttributesDtoVehicle::getAttributes)
                .toList();

    }

    private String getDictionaryWojewodztwoZachPomFilter() {
        var dictionaryDto = Optional.ofNullable(slownikiApi.getSlownik(WOJEWODZTWA_DICTIONARY)
                .block()).orElseThrow();

        DictionaryDto dto = Objects.requireNonNull(dictionaryDto.getData()).getAttributes();

        return Optional.ofNullable(dto.getDostepneRekordySlownika())
                .flatMap(records -> records.stream()
                        .filter(record -> WOJEWODZTWO_ZACHODNIOPOMORSKIE.equalsIgnoreCase(record.getWartoscSlownika()))
                        .findAny()).orElseThrow(() -> new NotFoundException(
                        "Cannot find value for dictionary element: [%s]".formatted(WOJEWODZTWO_ZACHODNIOPOMORSKIE)
                )).getKluczSlownika();

    }
}
