package backend.plugfinder;

import backend.plugfinder.controllers.ChargerTypeController;
import backend.plugfinder.controllers.dto.ChargerTypeDto;
import backend.plugfinder.services.models.ChargerTypeModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ChargerTypeController.class)
public class ChargerTypeControllerTest extends AbstractBaseControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void should_register_charger_type() throws Exception {
        // Creem el model charger type dto que enviarem com a Request Body
        ChargerTypeDto charger_type_dto = new ChargerTypeDto();
        charger_type_dto.setName("test");

        ObjectMapper objectMapper = new ObjectMapper();
        String typeJson = objectMapper.writeValueAsString(charger_type_dto);

        // Charger Type Model que rebrem del servei
        ChargerTypeModel expected_charger_type_model = new ChargerTypeModel();
        expected_charger_type_model.setType_id(1L);
        expected_charger_type_model.setName("test");

        // Fem que el servei retorni el charger type model creat previament
        when(charger_type_service.save_charger_type(any(ChargerTypeModel.class))).thenReturn(expected_charger_type_model);

        mockMvc.perform(MockMvcRequestBuilders.post("/chargertype")
                .contentType(MediaType.APPLICATION_JSON)
                .content(typeJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.type_id").value(1))
                .andExpect(jsonPath("$.name").value("test"));

        verify(charger_type_service).save_charger_type(argThat(chargerTypeModel -> {
            return chargerTypeModel.getName().equals("test");
        }));
    }

    @Test
    public void should_return_charger_type_list() throws Exception {
        ArrayList<ChargerTypeModel> expected_charger_types = new ArrayList<>();
        ChargerTypeModel charger_type_model = new ChargerTypeModel();
        charger_type_model.setType_id(1L);
        charger_type_model.setName("test");
        expected_charger_types.add(charger_type_model);

        when(charger_type_service.get_chargers_types()).thenReturn(expected_charger_types);
        mockMvc.perform(MockMvcRequestBuilders.get("/chargertype"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].type_id").value(1))
                .andExpect(jsonPath("$[0].name").value("test"));

    }

    @Test public void should_return_charger_type() throws Exception {
        ChargerTypeModel expected_charger_type_model = new ChargerTypeModel();
        expected_charger_type_model.setType_id(1L);
        expected_charger_type_model.setName("test");

        when(charger_type_service.get_charger_type_by_id(1)).thenReturn((expected_charger_type_model));
        mockMvc.perform(get("/chargertype/id/1", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.type_id").value(1))
                .andExpect(jsonPath("$.name").value("test"));
    }

    @Test
    public void should_return_charger_type_not_found() throws Exception {
        when(charger_type_service.get_charger_type_by_id(1)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "El charger type no existe"));
        mockMvc.perform(get("/chargertype/id/1", 1))
                .andExpect(status().isNotFound());
    }
}
