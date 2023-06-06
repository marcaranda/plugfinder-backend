package backend.plugfinder;

import backend.plugfinder.controllers.ChargerController;
import backend.plugfinder.controllers.dto.ChargerDto;
import backend.plugfinder.services.ChargerService;
import backend.plugfinder.services.models.ChargerModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ChargerController.class)
public class ChargerControllerTest extends AbstractBaseControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ChargerService charger_service;

    //region Get Methods

    @Test
    public void test_get_chargers() throws Exception {
        //Llista de chargers que retornarem
        ArrayList<ChargerModel> expected_chargers = new ArrayList<>();
        ChargerModel charger1 = new ChargerModel();
        charger1.setId_charger(1L);
        charger1.setLatitude(1.0);
        charger1.setLongitude(1.0);
        charger1.setIs_public(false);
        expected_chargers.add(charger1);

        ChargerModel charger2 = new ChargerModel();
        charger2.setId_charger(2L);
        charger2.setLatitude(2.0);
        charger2.setLongitude(2.0);
        charger2.setIs_public(false);
        expected_chargers.add(charger2);

        when(charger_service.buscar_cargadores(null, null, null, null, null, null)).thenReturn(expected_chargers);

        //Fem la crida al endpoint
        mockMvc.perform(get("/chargers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id_charger").value(1L))
                .andExpect(jsonPath("$[0].latitude").value(1.0))
                .andExpect(jsonPath("$[0].longitude").value(1.0))
                .andExpect(jsonPath("$[0].is_public").value(false))
                .andExpect(jsonPath("$[1].id_charger").value(2L))
                .andExpect(jsonPath("$[1].latitude").value(2.0))
                .andExpect(jsonPath("$[1].longitude").value(2.0))
                .andExpect(jsonPath("$[1].is_public").value(false));

        when(charger_service.buscar_cargadores(null, null, null, null, null, null)).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/chargers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(0));
    }


    @Test
    public void should_return_charger() throws Exception {
        ChargerModel expected_charger = new ChargerModel();
        expected_charger.setId_charger(1L);
        expected_charger.setLatitude(1.0);
        expected_charger.setLongitude(1.0);
        expected_charger.setIs_public(false);

        when(charger_service.find_charger_by_id(1L)).thenReturn(expected_charger);

        mockMvc.perform(get("/chargers/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id_charger").value(1L))
                .andExpect(jsonPath("$.latitude").value(1.0))
                .andExpect(jsonPath("$.longitude").value(1.0))
                .andExpect(jsonPath("$.is_public").value(false));
    }

    @Test
    public void should_return_charger_not_found() throws Exception {
        when(charger_service.find_charger_by_id(1L)).thenReturn(null);

        mockMvc.perform(get("/chargers/1"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void should_return_user_chargers() throws Exception {
        Long user_id = 1L;
        ArrayList<ChargerModel> expected_chargers = new ArrayList<>();
        ChargerModel charger1 = new ChargerModel();
        charger1.setId_charger(1L);
        charger1.setLatitude(1.0);
        charger1.setLongitude(1.0);
        charger1.setIs_public(false);
        expected_chargers.add(charger1);

        ChargerModel charger2 = new ChargerModel();
        charger2.setId_charger(2L);
        charger2.setLatitude(2.0);
        charger2.setLongitude(2.0);
        charger2.setIs_public(false);
        expected_chargers.add(charger2);

        when(charger_service.get_user_chargers(user_id)).thenReturn(expected_chargers);

        mockMvc.perform(get("/chargers/user/" + user_id))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id_charger").value(1L))
                .andExpect(jsonPath("$[0].latitude").value(1.0))
                .andExpect(jsonPath("$[0].longitude").value(1.0))
                .andExpect(jsonPath("$[0].is_public").value(false))
                .andExpect(jsonPath("$[1].id_charger").value(2L))
                .andExpect(jsonPath("$[1].latitude").value(2.0))
                .andExpect(jsonPath("$[1].longitude").value(2.0))
                .andExpect(jsonPath("$[1].is_public").value(false));
    }
    //endregion

    //region Post Methods
    @Test
    public void should_save_charger() throws Exception {
        ChargerDto charger = new ChargerDto();
        charger.setLatitude(1.0);
        charger.setLongitude(1.0);
        charger.setIs_public(false);

        ChargerModel expected_charger = new ChargerModel();
        expected_charger.setId_charger(1L);
        expected_charger.setLatitude(1.0);
        expected_charger.setLongitude(1.0);
        expected_charger.setIs_public(false);

        ObjectMapper objectMapper = new ObjectMapper();
        String chargerJson = objectMapper.writeValueAsString(charger);

        when(charger_service.save_charger(any(ChargerModel.class))).thenReturn(expected_charger);

        mockMvc.perform(post("/chargers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(chargerJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id_charger").value(1L))
                .andExpect(jsonPath("$.latitude").value(1.0))
                .andExpect(jsonPath("$.longitude").value(1.0))
                .andExpect(jsonPath("$.is_public").value(false));

    }
    //endregion

    //region Put Methods
    @Test
    public void should_update_charger() throws Exception {
        ChargerDto charger = new ChargerDto();
        charger.setId_charger(1L);
        charger.setLatitude(1.0);
        charger.setLongitude(1.0);
        charger.setIs_public(false);

        ChargerModel expected_charger = new ChargerModel();
        expected_charger.setId_charger(1L);
        expected_charger.setLatitude(1.0);
        expected_charger.setLongitude(1.0);
        expected_charger.setIs_public(false);

        ObjectMapper objectMapper = new ObjectMapper();
        String chargerJson = objectMapper.writeValueAsString(charger);

        when(charger_service.update_charger(any(ChargerModel.class))).thenReturn(expected_charger);

        mockMvc.perform(put("/chargers/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(chargerJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id_charger").value(1L))
                .andExpect(jsonPath("$.latitude").value(1.0))
                .andExpect(jsonPath("$.longitude").value(1.0))
                .andExpect(jsonPath("$.is_public").value(false));

    }

    @Test
    public void should_active_charger() throws Exception {
        Long charger_id = 1L;
        ChargerModel expected_charger = new ChargerModel();
        expected_charger.setId_charger(1L);
        expected_charger.setLatitude(1.0);
        expected_charger.setLongitude(1.0);
        expected_charger.setIs_public(false);
        expected_charger.setActive(true);

        when(charger_service.active_charger(charger_id)).thenReturn(expected_charger);

        mockMvc.perform(put("/chargers/{charger_id}/active", charger_id))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id_charger").value(1L))
                .andExpect(jsonPath("$.latitude").value(1.0))
                .andExpect(jsonPath("$.longitude").value(1.0))
                .andExpect(jsonPath("$.is_public").value(false))
                .andExpect(jsonPath("$.active").value(true));
    }
    //endregion

    //region Delete Methods
    @Test
    public void should_delete_charger() throws Exception {
        Long charger_id = 1L;

        when(charger_service.delete_charger(charger_id)).thenReturn("Se elimino correctamente el cargador");

        mockMvc.perform(delete("/chargers/{charger_id}", charger_id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Se elimino correctamente el cargador"));
    }

    @Test
    public void should_return_charger_delete_not_found() throws Exception {
        Long charger_id = 1L;

        when(charger_service.delete_charger(charger_id)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "El cargador no existe"));

        mockMvc.perform(delete("/chargers/{charger_id}", charger_id))
                .andExpect(status().isNotFound());
    }
    //endregion
}
