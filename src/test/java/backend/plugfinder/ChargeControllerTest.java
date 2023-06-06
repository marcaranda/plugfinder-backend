package backend.plugfinder;

import backend.plugfinder.controllers.ChargeController;
import backend.plugfinder.controllers.CommentController;
import backend.plugfinder.controllers.dto.ChargeDto;
import backend.plugfinder.services.ChargeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ChargeController.class)
public class ChargeControllerTest extends AbstractBaseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    private ChargeService chargeService;

    private ChargeController chargeController;


    @Test
    public void testGetCharge() throws Exception {
        // Arrange
        Long chargeId = 1L;
        ArrayList<ChargeDto> expectedCharges = new ArrayList<>();
        when(chargeService.get_charges(chargeId)).thenReturn(expectedCharges);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/charges")
                        .param("id", chargeId.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }

    @Test
    public void testSaveCharge() throws Exception {
        // Arrange
        ChargeDto chargeDto = new ChargeDto();
        ChargeDto savedChargeDto = new ChargeDto();
        when(chargeService.save_charge(chargeDto)).thenReturn(savedChargeDto);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/charges")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"property\":\"value\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.property").value("value"));
    }

    @Test
    public void testCreateChargeFromReservation() throws Exception {
        // Arrange
        Long reservationId = 1L;
        ChargeDto createdChargeDto = new ChargeDto();
        when(chargeService.save_charge_from_reservation(reservationId)).thenReturn(createdChargeDto);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/charges/new_from_reservation")
                        .param("reservation_id", reservationId.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.property").value("value"));
    }

    @Test
    public void testEndCharge() throws Exception {
        // Arrange
        Long chargeId = 1L;
        ChargeDto chargeDto = new ChargeDto();
        ChargeDto endedChargeDto = new ChargeDto();
        when(chargeService.get_charge(chargeId)).thenReturn(chargeDto);
        when(chargeService.end_charge(chargeDto)).thenReturn(endedChargeDto);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/charges/{charge_id}/end", chargeId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.property").value("value"));
    }
}
