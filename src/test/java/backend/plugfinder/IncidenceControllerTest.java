package backend.plugfinder;

import backend.plugfinder.controllers.IncidenceController;
import backend.plugfinder.controllers.dto.ChargerDto;
import backend.plugfinder.controllers.dto.IncidenceDto;
import backend.plugfinder.controllers.dto.UserDto;
import backend.plugfinder.services.IncidenceService;
import backend.plugfinder.services.models.ChargerModel;
import backend.plugfinder.services.models.IncidenceModel;
import backend.plugfinder.services.models.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(IncidenceController.class)
public class IncidenceControllerTest extends AbstractBaseControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private IncidenceService incidence_service;

    //region Get methods

    @Test
    public void get_charger_incidences() throws Exception {
        //region Definición de datos de prueba
        ArrayList<IncidenceModel> incidences = new ArrayList<>();
        UserModel userModel = new UserModel();
        userModel.setUser_id(1L);
        userModel.setUsername("test");
        userModel.setReal_name("test");
        userModel.setEmail("test@test.com");

        ChargerModel chargerModel = new ChargerModel();
        chargerModel.setId_charger(1L);
        chargerModel.setLatitude(1.0);
        chargerModel.setLongitude(1.0);
        chargerModel.setIs_public(false);


        IncidenceModel incidence = new IncidenceModel();
        incidence.setIncidence("First incidence");
        incidence.setUser(userModel);
        incidence.setCharger(chargerModel);
        incidence.setTitle("The charger does not work");
        incidences.add(incidence);

        IncidenceModel incidence2 = new IncidenceModel();
        incidence2.setIncidence("Second incidence");
        incidence2.setUser(userModel);
        incidence2.setCharger(chargerModel);
        incidence2.setTitle("The plug is broken");
        incidences.add(incidence2);
        //endregion

        when(incidence_service.get_charger_incidences(1L)).thenReturn(incidences);

        // Realizar la solicitud GET a la ruta "/charger/{charger_id}"
        mockMvc.perform(get("/incidence/charger/{charger_id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].incidence").value("First incidence"))
                .andExpect(jsonPath("$[0].user.user_id").value(1))
                .andExpect(jsonPath("$[0].charger.id_charger").value(1))
                .andExpect(jsonPath("$[0].title").value("The charger does not work"))
                .andExpect(jsonPath("$[1].incidence").value("Second incidence"))
                .andExpect(jsonPath("$[1].user.user_id").value(1))
                .andExpect(jsonPath("$[1].charger.id_charger").value(1))
                .andExpect(jsonPath("$[1].title").value("The plug is broken"));
    }

    @Test
    public void get_user_incidences() throws Exception {
        //region Definición de datos de prueba
        ArrayList<IncidenceModel> incidences = new ArrayList<>();
        UserModel userModel = new UserModel();
        userModel.setUser_id(1L);
        userModel.setUsername("test");
        userModel.setReal_name("test");
        userModel.setEmail("test@test.com");

        UserModel userModel2 = new UserModel();
        userModel2.setUser_id(2L);
        userModel2.setUsername("test2");
        userModel2.setReal_name("test2");
        userModel2.setEmail("test2@test.com");

        ChargerModel chargerModel = new ChargerModel();
        chargerModel.setId_charger(1L);
        chargerModel.setLatitude(1.0);
        chargerModel.setLongitude(1.0);
        chargerModel.setIs_public(false);


        IncidenceModel incidence = new IncidenceModel();
        incidence.setIncidence("First incidence");
        incidence.setUser(userModel);
        incidence.setCharger(chargerModel);
        incidence.setTitle("The charger does not work");
        incidences.add(incidence);

        IncidenceModel incidence2 = new IncidenceModel();
        incidence2.setIncidence("Second incidence");
        incidence2.setUser(userModel);
        incidence2.setCharger(chargerModel);
        incidence2.setTitle("The plug is broken");
        incidences.add(incidence2);
        //endregion

        when(incidence_service.get_user_incidences(1L)).thenReturn(incidences);
        when(incidence_service.get_user_incidences(2L)).thenReturn(new ArrayList<>());

        // Realizar la solicitud GET a la ruta "/charger/{charger_id}"
        mockMvc.perform(get("/incidence/user/{user_id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].incidence").value("First incidence"))
                .andExpect(jsonPath("$[0].user.user_id").value(1))
                .andExpect(jsonPath("$[0].charger.id_charger").value(1))
                .andExpect(jsonPath("$[0].title").value("The charger does not work"))
                .andExpect(jsonPath("$[1].incidence").value("Second incidence"))
                .andExpect(jsonPath("$[1].user.user_id").value(1))
                .andExpect(jsonPath("$[1].charger.id_charger").value(1))
                .andExpect(jsonPath("$[1].title").value("The plug is broken"));

        mockMvc.perform(get("/incidence/user/{user_id}", 2L))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(0));
    }
    //endregion

    //region Post methods

    @Test
    public void post_incidence() throws Exception {
        //region Definición de datos de prueba
        UserDto user = new UserDto();
        user.setReal_name("test");
        user.setUser_id(1L);
        user.setPassword("test");
        user.setEmail("test@test.com");

        ChargerDto charger = new ChargerDto();
        charger.setId_charger(1L);
        charger.setIs_public(false);
        charger.setLongitude(1.0);
        charger.setLatitude(1.0);

        IncidenceDto incidenceDto = new IncidenceDto();
        incidenceDto.setIncidence("First incidence");
        incidenceDto.setUser(user);
        incidenceDto.setCharger(charger);
        incidenceDto.setTitle("The charger does not work");

        ObjectMapper objectMapper = new ObjectMapper();
        String incidenceJson = objectMapper.writeValueAsString(incidenceDto);

        UserModel userModel = new UserModel();
        userModel.setUser_id(1L);
        userModel.setUsername("test");
        userModel.setReal_name("test");
        userModel.setEmail("test@test.com");

        ChargerModel chargerModel = new ChargerModel();
        chargerModel.setId_charger(1L);
        chargerModel.setLatitude(1.0);
        chargerModel.setLongitude(1.0);
        chargerModel.setIs_public(false);


        IncidenceModel incidence = new IncidenceModel();
        incidence.setIncidence_id(1L);
        incidence.setIncidence("First incidence");
        incidence.setUser(userModel);
        incidence.setCharger(chargerModel);
        incidence.setTitle("The charger does not work");

        when(incidence_service.save_incidence(any(IncidenceModel.class))).thenReturn(incidence);
        //endregion

        // Realizar la solicitud POST a la ruta "/comment"
        mockMvc.perform(MockMvcRequestBuilders.post("/incidence")
                .contentType(MediaType.APPLICATION_JSON)
                .content(incidenceJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.incidence_id").value(1))
                .andExpect(jsonPath("$.incidence").value("First incidence"))
                .andExpect(jsonPath("$.user.user_id").value(1))
                .andExpect(jsonPath("$.charger.id_charger").value(1))
                .andExpect(jsonPath("$.title").value("The charger does not work"));
    }

    //endregion

    //region Put methods

    @Test
    public void should_edit_incidence() throws Exception {
        //region Definición de datos de prueba
        UserDto user = new UserDto();
        user.setReal_name("test");
        user.setUser_id(1L);
        user.setPassword("test");
        user.setEmail("test@test.com");

        ChargerDto charger = new ChargerDto();
        charger.setId_charger(1L);
        charger.setIs_public(false);
        charger.setLongitude(1.0);
        charger.setLatitude(1.0);

        IncidenceDto incidenceDto = new IncidenceDto();
        incidenceDto.setIncidence_id(1L);
        incidenceDto.setIncidence("Incidence edited");
        incidenceDto.setUser(user);
        incidenceDto.setCharger(charger);
        incidenceDto.setTitle("The charger does not work");

        ObjectMapper objectMapper = new ObjectMapper();
        String commentJson = objectMapper.writeValueAsString(incidenceDto);

        UserModel userModel = new UserModel();
        userModel.setUser_id(1L);
        userModel.setUsername("test");
        userModel.setReal_name("test");
        userModel.setEmail("test@test.com");

        ChargerModel chargerModel = new ChargerModel();
        chargerModel.setId_charger(1L);
        chargerModel.setLatitude(1.0);
        chargerModel.setLongitude(1.0);
        chargerModel.setIs_public(false);


        IncidenceModel incidence = new IncidenceModel();
        incidence.setIncidence_id(1L);
        incidence.setIncidence("Incidence edited");
        incidence.setUser(userModel);
        incidence.setCharger(chargerModel);
        incidence.setTitle("The charger does not work");

        //endregion

        when(incidence_service.update_incidence(any(IncidenceModel.class))).thenReturn(incidence);

        // Realizar la solicitud POST a la ruta "/comment"
        mockMvc.perform(MockMvcRequestBuilders.put("/incidence")
                .contentType(MediaType.APPLICATION_JSON)
                .content(commentJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.incidence_id").value(1))
                .andExpect(jsonPath("$.incidence").value("Incidence edited"))
                .andExpect(jsonPath("$.user.user_id").value(1))
                .andExpect(jsonPath("$.charger.id_charger").value(1))
                .andExpect(jsonPath("$.title").value("The charger does not work"));
    }

    @Test
    public void should_return_not_found() throws Exception {
        //region Definición de datos de prueba
        UserDto user = new UserDto();
        user.setReal_name("test");
        user.setUser_id(1L);
        user.setPassword("test");
        user.setEmail("test@test.com");

        ChargerDto charger = new ChargerDto();
        charger.setId_charger(1L);
        charger.setIs_public(false);
        charger.setLongitude(1.0);
        charger.setLatitude(1.0);

        IncidenceDto incidenceDto = new IncidenceDto();
        incidenceDto.setIncidence_id(1L);
        incidenceDto.setIncidence("Incidence edited");
        incidenceDto.setUser(user);
        incidenceDto.setCharger(charger);
        incidenceDto.setTitle("The plug does not work");

        ObjectMapper objectMapper = new ObjectMapper();
        String commentJson = objectMapper.writeValueAsString(incidenceDto);

        UserModel userModel = new UserModel();
        userModel.setUser_id(1L);
        userModel.setUsername("test");
        userModel.setReal_name("test");
        userModel.setEmail("test@test.com");

        ChargerModel chargerModel = new ChargerModel();
        chargerModel.setId_charger(1L);
        chargerModel.setLatitude(1.0);
        chargerModel.setLongitude(1.0);
        chargerModel.setIs_public(false);


        IncidenceModel incidence = new IncidenceModel();
        incidence.setIncidence_id(1L);
        incidence.setIncidence("Incidence edited");
        incidence.setUser(userModel);
        incidence.setCharger(chargerModel);
        incidence.setTitle("The plug does not work");

        //endregion

        when(incidence_service.update_incidence(any(IncidenceModel.class))).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "La incidencia no existe."));

        // Realizar la solicitud POST a la ruta "/comment"
        mockMvc.perform(MockMvcRequestBuilders.put("/incidence")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commentJson))
                .andExpect(status().isNotFound());
    }
    //endregion

    //region Delete methods

    @Test
    public void should_delete_incidence() throws Exception {
        Long incidenceId = 1L;
        when(incidence_service.delete_incidence(incidenceId)).thenReturn(new ResponseEntity<String>("Incidencia eliminada", HttpStatus.OK));

        mockMvc.perform(delete("/incidence/{incidenceId}", incidenceId))
                .andExpect(status().isOk())
                .andExpect(content().string("Incidencia eliminada"));
    }

    @Test
    public void should_return_incidence_not_found_delete() throws Exception {
        Long incidenceId = 1L;
        when(incidence_service.delete_incidence(incidenceId)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "El comentario no existe."));

        mockMvc.perform(delete("/incidence/{incidenceId}", incidenceId))
                .andExpect(status().isNotFound());
    }
    //endregion
}
