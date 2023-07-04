package pl.mareczek100.api.cepik.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import pl.mareczek100.api.cepik.cepik_dto.CepikVehicleDTO;
import pl.mareczek100.api.cepik.cepik_dto.mapperdto.CepikVehicleMapper;
import pl.mareczek100.domain.cepik_api_domain.CepikVehicle;
import pl.mareczek100.service.cepik_service.CepikVehicleService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CepikVehicleRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class CepikVehicleRestControllerTest {

    private final static String CEPIK_VEHICLE_HOME = "/api/cepik";
    private final static String CEPIK_VEHICLE = "/vehicle";
    private final static String CEPIK_VEHICLES = "/vehicles";

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @MockBean
    private CepikVehicleService cepikService;

    @MockBean
    private CepikVehicleMapper cepikVehicleMapper;

    @Test
    void showCepikRandomVehicleByDateFromAndVehicleIdCorrectly() throws Exception {
        // given
        String cepikVehicleId = "5";
        LocalDate dateFrom = LocalDate.of(2020, 1, 1);

        CepikVehicleDTO cepikVehicleDTO = someCepikVehicleDTO();
        CepikVehicle cepikVehicle = someCepikVehicle();

        // when
        when(cepikService.findCepikVehicle(cepikVehicleId, dateFrom)).thenReturn(cepikVehicle);
        when(cepikVehicleMapper.mapToDto(cepikVehicle)).thenReturn(cepikVehicleDTO);
        String responseBody = objectMapper.writeValueAsString(cepikVehicleDTO);

        // then
        MvcResult result = mockMvc.perform(get(CEPIK_VEHICLE_HOME + CEPIK_VEHICLE)
                        .param("cepikVehicleId", cepikVehicleId)
                        .param("dateFrom", dateFrom.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand", is(cepikVehicleDTO.brand())))
                .andExpect(jsonPath("$.model", is(cepikVehicleDTO.model())))
                .andExpect(jsonPath("$.type", is(cepikVehicleDTO.type())))
                .andExpect(jsonPath("$.engineCapacity", is(cepikVehicleDTO.engineCapacity()), BigDecimal.class))
                .andExpect(jsonPath("$.weight", is(cepikVehicleDTO.weight())))
                .andExpect(jsonPath("$.fuel", is(cepikVehicleDTO.fuel())))
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(responseBody);
    }

    @Test
    void showCepikVehicleListCorrectly() throws Exception {

        // given
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        LocalDate dateFrom = LocalDate.of(2020, 1, 1);
        LocalDate dateTo = LocalDate.of(2022, 12, 31);
        parameters.add("dateFrom", dateFrom.toString());
        parameters.add("dateTo", dateTo.toString());

        CepikVehicle cepikVehicle = someCepikVehicle();
        CepikVehicleDTO cepikVehicleDTO = someCepikVehicleDTO();
        List<CepikVehicle> cepikVehicleList = List.of(cepikVehicle);
        List<CepikVehicleDTO> cepikVehicleDTOList = List.of(CepikVehicleDTO.builder()
                        .brand(cepikVehicle.getBrand())
                        .model(cepikVehicle.getModel())
                        .type(cepikVehicle.getType())
                        .engineCapacity(cepikVehicle.getEngineCapacity())
                        .weight(cepikVehicle.getWeight())
                        .fuel(cepikVehicle.getFuel())
                .build());

        // when
        when(cepikService.findCepikVehiclesByDate(dateFrom, dateTo)).thenReturn(cepikVehicleList);
        when(cepikVehicleMapper.mapToDto(cepikVehicle)).thenReturn(cepikVehicleDTO);
        String responseBody = objectMapper.writeValueAsString(cepikVehicleDTOList);

        // then
        MvcResult result = mockMvc.perform(get(CEPIK_VEHICLE_HOME + CEPIK_VEHICLES)
                        .params(parameters))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].brand", is(cepikVehicleDTOList.get(0).brand())))
                .andExpect(jsonPath("$[0].model", is(cepikVehicleDTOList.get(0).model())))
                .andExpect(jsonPath("$[0].type", is(cepikVehicleDTOList.get(0).type())))
                .andExpect(jsonPath("$[0].engineCapacity", is(cepikVehicleDTOList.get(0).engineCapacity()), BigDecimal.class))
                .andExpect(jsonPath("$[0].weight", is(cepikVehicleDTOList.get(0).weight())))
                .andExpect(jsonPath("$[0].fuel", is(cepikVehicleDTOList.get(0).fuel())))
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(responseBody);
    }

    private static CepikVehicleDTO someCepikVehicleDTO() {
        return CepikVehicleDTO.builder()
                .brand("brand")
                .model("model")
                .type("type")
                .engineCapacity(new BigDecimal("1000"))
                .weight(1200)
                .fuel("fuel")
                .build();
    }

    private static CepikVehicle someCepikVehicle() {
        return CepikVehicle.builder()
                .id("id")
                .brand("brand")
                .model("model")
                .type("type")
                .engineCapacity(new BigDecimal("1000"))
                .weight(1200)
                .fuel("fuel")
                .build();
    }
}