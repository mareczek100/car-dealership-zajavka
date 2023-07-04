package pl.mareczek100.api.controller;


import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.mareczek100.api.dto.dtomapper.CarDtoMapper;
import pl.mareczek100.service.CarToSellService;
import pl.mareczek100.service.PurchaseCarService;

@WebMvcTest(controllers = CarPurchaseController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class CarPurchaseControllerTest {

    @MockBean
    private final PurchaseCarService purchaseCarService;

    @MockBean
    private final CarToSellService carToSellService;

    @MockBean
    private final CarDtoMapper carDtoMapper;

    private final MockMvc mockMvc;

    @BeforeEach
    void check() {
        Assertions.assertNotNull(purchaseCarService);
        Assertions.assertNotNull(carToSellService);
        Assertions.assertNotNull(carDtoMapper);
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    void homePage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/car"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("car"));

    }

    @Test
    void purchaseView() throws Exception {
        //given
        String carVin = "1G1PE5S97B7239380";

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/car/purchase/{carVin}", carVin))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("carVin"))
                .andExpect(MockMvcResultMatchers.view().name("purchase"));
    }

    @Test
    void buyCar() throws Exception {
        //given
        String carVin = "123";
        String email = "test@test.pl";
        @Email String badEmail = "badEmailTest";
        String carBought = "carBought";

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/car/purchase/{carVin}", carVin)
                        .param("email", email))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists(carBought))
                .andExpect(MockMvcResultMatchers.view().name("purchase"));

        mockMvc.perform(MockMvcRequestBuilders.post("/car/purchase/{carVin}", carVin)
                        .param("email", badEmail))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist(carBought))
                .andExpect(MockMvcResultMatchers.view().name("error"));
    }

    @Test
    void emailValidationAndExceptionHandlerWorksFine() throws Exception {
        //given
        String badEmail = "badEmail";
        String carVin = "123";
        String exceptionMessage = "must be a well-formed email address";

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/car/purchase/{carVin}", carVin)
                        .param("email", badEmail))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.model().attributeExists("errorMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("errorMessage", Matchers.containsString(exceptionMessage)))
                .andExpect(MockMvcResultMatchers.view().name("error"));
    }
}