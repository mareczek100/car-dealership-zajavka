package pl.mareczek100.api.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.mareczek100.api.dto.CarDTO;
import pl.mareczek100.api.dto.InvoiceDTO;
import pl.mareczek100.api.dto.dtomapper.CarDtoMapper;
import pl.mareczek100.api.dto.dtomapper.InvoiceDtoMapper;
import pl.mareczek100.domain.CarToSellTempStorage;
import pl.mareczek100.domain.Invoice;
import pl.mareczek100.service.CarToSellService;
import pl.mareczek100.service.PurchaseCarService;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = CarPurchaseRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class CarPurchaseRestControllerTest {
    private final static String API_PURCHASE_HOME = "/api/car";
    private final static String API_PURCHASE_BUY = "/purchase/{carVin}";

    @MockBean
    private final PurchaseCarService purchaseCarService;

    @MockBean
    private final CarToSellService carToSellService;

    @MockBean
    private final InvoiceDtoMapper invoiceDtoMapper;

    @MockBean
    private final CarDtoMapper carDtoMapper;

    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;

    @BeforeEach
    void check() {
        Assertions.assertNotNull(purchaseCarService);
        Assertions.assertNotNull(carToSellService);
        Assertions.assertNotNull(invoiceDtoMapper);
        Assertions.assertNotNull(carDtoMapper);
        Assertions.assertNotNull(objectMapper);
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    void homePage() throws Exception {
        //given
        CarToSellTempStorage carToSellTempStorage1 = CarToSellTempStorage.builder().vin("1").build();
        CarToSellTempStorage carToSellTempStorage2 = CarToSellTempStorage.builder().vin("2").build();
        CarToSellTempStorage carToSellTempStorage3 = CarToSellTempStorage.builder().vin("3").build();
        CarDTO.CarDTOBuilder carDTO1build = CarDTO.builder();
        CarDTO.CarDTOBuilder carDTO2build = CarDTO.builder();
        CarDTO.CarDTOBuilder carDTO3build = CarDTO.builder();
        List<CarToSellTempStorage> availableCars =
                List.of(carToSellTempStorage1, carToSellTempStorage2, carToSellTempStorage3);

        //when
        Mockito.when(carToSellService.findAllAvailableCarsToSell()).thenReturn(availableCars);
        CarDTO carDTO1 = carDTO1build.vin(availableCars.get(0).getVin()).build();
        Mockito.when(carDtoMapper.mapToDTO(availableCars.get(0)))
                .thenReturn(carDTO1);
        CarDTO carDTO2 = carDTO2build.vin(availableCars.get(1).getVin()).build();
        Mockito.when(carDtoMapper.mapToDTO(availableCars.get(1)))
                .thenReturn(carDTO2);
        CarDTO carDTO3 = carDTO3build.vin(availableCars.get(2).getVin()).build();
        Mockito.when(carDtoMapper.mapToDTO(availableCars.get(2)))
                .thenReturn(carDTO3);

        List<CarDTO> carDTOs =
                List.of(carDTO1, carDTO2, carDTO3);
        String responseBodyAvailableCars = objectMapper.writeValueAsString(carDTOs);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(API_PURCHASE_HOME))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].vin", is(carDTOs.get(0).vin())))
                .andExpect(jsonPath("$[1].vin", is(carDTOs.get(1).vin())))
                .andExpect(jsonPath("$[2].vin", is(carDTOs.get(2).vin())))
                .andExpect(jsonPath("$[0].brand", is(carDTOs.get(0).brand())))
                .andExpect(jsonPath("$[0].model", is(carDTOs.get(0).model())))
                .andExpect(jsonPath("$[0].year", is(carDTOs.get(0).year()), Short.class))
                .andExpect(jsonPath("$[0].color", is(carDTOs.get(0).color())))
                .andExpect(jsonPath("$[0].price", is(carDTOs.get(0).price()), BigDecimal.class))
                .andReturn();

        org.assertj.core.api.Assertions
                .assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(responseBodyAvailableCars);
    }

    @Test
    void purchaseView() throws Exception {
        //given
        String carVin = "123";
        CarToSellTempStorage carToSellTempStorage =
                CarToSellTempStorage.builder().vin(carVin).build();
        CarDTO carDTO =
                pl.mareczek100.api.dto.CarDTO.builder().vin(carToSellTempStorage.getVin()).build();

        //when
        Mockito.when(carToSellService.findAvailableCarToSell(carVin)).thenReturn(carToSellTempStorage);
        Mockito.when(carDtoMapper.mapToDTO(carToSellTempStorage)).thenReturn(carDTO);
        String responseBody = objectMapper.writeValueAsString(carDTO);

        //then
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get(API_PURCHASE_HOME + API_PURCHASE_BUY, carVin))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(jsonPath("$.vin", is(carDTO.vin())))
                .andExpect(jsonPath("$.vin", is(carDTO.vin())))
                .andExpect(jsonPath("$.vin", is(carDTO.vin())))
                .andExpect(jsonPath("$.brand", is(carDTO.brand())))
                .andExpect(jsonPath("$.model", is(carDTO.model())))
                .andExpect(jsonPath("$.year", is(carDTO.year()), Short.class))
                .andExpect(jsonPath("$.color", is(carDTO.color())))
                .andExpect(jsonPath("$.price", is(carDTO.price()), BigDecimal.class))
                .andReturn();

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(responseBody);
    }

    @Test
    void buyCarWithValidCustomerEmail() throws Exception {
        //given
        String invoiceNumber = "123";
        String carVin = "123";
        String validEmail = "email@email.com";

        Invoice invoice = Invoice.builder()
                .invoiceNumber(invoiceNumber)
                .dateTime(OffsetDateTime.now()).build();
        InvoiceDTO invoiceDTO = InvoiceDTO.builder()
                .invoiceNumber(invoice.getInvoiceNumber())
                .dateTime(invoice.getDateTime()).build();

        //when
        Mockito.when(purchaseCarService.buyANewCar(carVin, validEmail)).thenReturn(invoice);
        Mockito.when(invoiceDtoMapper.mapToDTO(invoice)).thenReturn(invoiceDTO);
        String responseBody = objectMapper.writeValueAsString(invoiceDTO);

        //then
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post(API_PURCHASE_HOME + API_PURCHASE_BUY, carVin)
                                .param("email", validEmail))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(jsonPath("$.invoiceNumber", is(invoiceDTO.invoiceNumber())))
                .andExpect(jsonPath("$.dateTime",
                        is(invoiceDTO.dateTime().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)), OffsetDateTime.class))
                .andReturn();

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(responseBody);
    }

    @Test
    void buyCarWithBadCustomerEmail() throws Exception {
        //given
        String invoiceNumber = "123";
        String carVin = "123";
        String badEmail = "badEmail";

        Invoice invoice = Invoice.builder().invoiceNumber(invoiceNumber).dateTime(OffsetDateTime.now()).build();
        InvoiceDTO invoiceDTO = InvoiceDTO.builder()
                .invoiceNumber(invoice.getInvoiceNumber())
                .dateTime(invoice.getDateTime()).build();

        //when
        Mockito.when(purchaseCarService.buyANewCar(carVin, badEmail)).thenReturn(invoice);
        Mockito.when(invoiceDtoMapper.mapToDTO(invoice)).thenReturn(invoiceDTO);
        String responseBody = objectMapper.writeValueAsString(invoiceDTO);

        //then
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post(API_PURCHASE_HOME + API_PURCHASE_BUY, carVin)
                                .param("email", badEmail))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorId", notNullValue()))
                .andReturn();

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString()).isNotEmpty();
        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString()).contains("errorId");
    }
}