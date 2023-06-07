package backend.plugfinder;

import backend.plugfinder.controllers.CarController;
import backend.plugfinder.controllers.dto.CarDto;
import backend.plugfinder.helpers.CarId;
import backend.plugfinder.services.models.CarModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(CarController.class)
public class CarControllerTest extends AbstractBaseControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void should_register_car() throws Exception {
        // Creem el car dto que enviarem com a Request Body
        CarDto carDto = new CarDto();
        carDto.setId(new CarId("9999TTT", 1L));

        ObjectMapper objectMapper = new ObjectMapper();
        String carJson = objectMapper.writeValueAsString(carDto);

        // Car Model que rebrem del servei
        CarModel expected_car_model = new CarModel();
        expected_car_model.setId(new CarId("9999TTT", 1L));

        // Fem que el servei retorni el car model creat previament
        when(car_service.save_car(any(CarModel.class))).thenReturn(expected_car_model);

        mockMvc.perform(MockMvcRequestBuilders.post("/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(carJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id.license").value("9999TTT"))
                .andExpect(jsonPath("$.id.id").value(1));

        verify(car_service).save_car(argThat(carModel -> {
            return carModel.getId().getLicense().equals("9999TTT") &&
                    carModel.getId().getId() == 1;
        }));
    }
}
