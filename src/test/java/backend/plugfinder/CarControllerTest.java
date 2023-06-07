package backend.plugfinder;

import backend.plugfinder.controllers.CarController;
import backend.plugfinder.controllers.dto.CarDto;
import backend.plugfinder.helpers.CarId;
import backend.plugfinder.services.models.CarModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

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

    @Test
    public void should_return_user_car_list() throws Exception {
        ArrayList<CarModel> expected_cars = new ArrayList<>();
        CarModel car_model = new CarModel();
        car_model.setId(new CarId("9999TTT", 1L));
        expected_cars.add(car_model);

        when(car_service.get_cars(1L)).thenReturn(expected_cars);
        mockMvc.perform(MockMvcRequestBuilders.get("/cars?user_id=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id.license").value("9999TTT"))
                .andExpect(jsonPath("$[0].id.id").value(1));
    }

    @Test
    public void should_return_car() throws Exception {
        CarModel expected_car_model = new CarModel();
        expected_car_model.setId(new CarId("9999TTT", 1L));

        when(car_service.get_car_by_id("9999TTT", 1)).thenReturn(expected_car_model);
        mockMvc.perform(MockMvcRequestBuilders.get("/cars/license/9999TTT/user_id/1", "9999TTT", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id.license").value("9999TTT"))
                .andExpect(jsonPath("$.id.id").value(1));
    }

    @Test
    public void should_return_car_not_found() throws Exception {
        when(car_service.get_car_by_id("9999TTL", 1)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "El coche no existe"));
        mockMvc.perform(MockMvcRequestBuilders.get("/cars/license/9999TTL/user_id/1", "9999TTL", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_return_default_car() throws Exception {
        CarModel expected_car_model = new CarModel();
        expected_car_model.setId(new CarId("9999TTT", 1L));
        expected_car_model.setDefault_car(true);

        when(car_service.get_default_car(1L)).thenReturn(expected_car_model);
        mockMvc.perform(MockMvcRequestBuilders.get("/cars/user_id/1/default", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id.license").value("9999TTT"))
                .andExpect(jsonPath("$.id.id").value(1))
                .andExpect(jsonPath("$.default_car").value(true));
    }

    @Test
    public void should_return_user_not_found() throws Exception {
        when(car_service.get_default_car(2L)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario no existe"));
        mockMvc.perform(MockMvcRequestBuilders.get("/cars/user_id/2/default", 2))
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_edit_car() throws Exception {
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

        mockMvc.perform(MockMvcRequestBuilders.put("/cars")
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

    @Test
    public void should_set_default_car() throws Exception {
        CarModel expected_car_model = new CarModel();
        expected_car_model.setId(new CarId("9999TTT", 1L));
        expected_car_model.setDefault_car(true);

        when(car_service.default_car("9999TTT", 1L)).thenReturn(expected_car_model);
        mockMvc.perform(MockMvcRequestBuilders.put("/cars/license/9999TTT/user_id/1/default", "9999TTT", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id.license").value("9999TTT"))
                .andExpect(jsonPath("$.id.id").value(1))
                .andExpect(jsonPath("$.default_car").value(true));
    }

    @Test
    public void should_delete_car() throws Exception {
        when(car_service.delete_car("9999TTT", 1L)).thenReturn("Se elimino correctamente el coche con matricula 9999TTT");

        mockMvc.perform(MockMvcRequestBuilders.delete("/cars/license/9999TTT/user_id/1", "9999TTT", 1))
                .andExpect(status().isOk())
                .andExpect(content().string("Se elimino correctamente el coche con matricula 9999TTT"));
    }
}
