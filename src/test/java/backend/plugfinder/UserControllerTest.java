package backend.plugfinder;

import backend.plugfinder.controllers.UserController;
import backend.plugfinder.controllers.dto.ChargerDto;
import backend.plugfinder.controllers.dto.UserDto;
import backend.plugfinder.helpers.OurException;
import backend.plugfinder.services.models.ChargerModel;
import backend.plugfinder.services.models.UserModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserController.class)
public class UserControllerTest extends AbstractBaseControllerTest {
    @Autowired
    MockMvc mockMvc;

    //region Register
    @Test
    public void should_register_user() throws Exception {
        // Creem el user dto que enviarem com a Request Body
        UserDto user_dto = new UserDto();
        user_dto.setEmail("test");
        user_dto.setUsername("test");

        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user_dto);

        // User Model que rebrem del servei
        UserModel expected_user_model = new UserModel();
        expected_user_model.setUser_id(1L);
        expected_user_model.setEmail("test");
        expected_user_model.setUsername("test");

        // Fem que el servei retorni el user model creat previament
        when(user_service.user_register(any(UserModel.class))).thenReturn(expected_user_model);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.user_id").value(1))
                .andExpect(jsonPath("$.email").value("test"))
                .andExpect(jsonPath("$.username").value("test"));

        // Verifiquem que s'ha cridat al metode de registre amb l'usuari correcte
        verify(user_service).user_register(argThat(userModel -> {
            return userModel.getEmail().equals("test") &&
                    userModel.getUsername().equals("test");
        }));
    }

    @Test
    public void should_return_bad_email() throws Exception {
        // Creem el user dto que enviarem com a Request Body
        UserDto user_dto = new UserDto();
        user_dto.setEmail("test");
        user_dto.setUsername("test");

        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user_dto);

        // Fem que el servei retorni la excepció de bad email
        when(user_service.user_register(any(UserModel.class))).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "El correo electrónico no es válido."));;

        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isBadRequest());

        // Verifiquem que s'ha cridat al metode de registre amb l'usuari correcte
        verify(user_service).user_register(argThat(userModel -> {
            return userModel.getEmail().equals("test") &&
                    userModel.getUsername().equals("test");
        }));
    }

    @Test
    public void should_return_invalid_tlf() throws Exception {
        // Creem el user dto que enviarem com a Request Body
        UserDto user_dto = new UserDto();
        user_dto.setEmail("test");
        user_dto.setUsername("test");

        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user_dto);

        // Fem que el servei retorni la excepció de bad phone number
        when(user_service.user_register(any(UserModel.class))).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "El número de teléfono no es válido."));;

        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest());

        // Verifiquem que s'ha cridat al metode de registre amb l'usuari correcte
        verify(user_service).user_register(argThat(userModel -> {
            return userModel.getEmail().equals("test") &&
                    userModel.getUsername().equals("test");
        }));
    }

    @Test
    public void should_return_invalid_birth_date() throws Exception {
        // Creem el user dto que enviarem com a Request Body
        UserDto user_dto = new UserDto();
        user_dto.setEmail("test");
        user_dto.setUsername("test");

        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user_dto);

        // Fem que el servei retorni la excepció de bad birth_date
        when(user_service.user_register(any(UserModel.class))).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de nacimiento no es válida."));;

        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest());

        // Verifiquem que s'ha cridat al metode de registre amb l'usuari correcte
        verify(user_service).user_register(argThat(userModel -> {
            return userModel.getEmail().equals("test") &&
                    userModel.getUsername().equals("test");
        }));
    }
    //endregion

    //region View profile
    @Test
    public void should_return_user_profile() throws Exception {
        UserModel user = new UserModel();
        user.setUser_id(1L); user.setEmail("test"); user.setUsername("test");
        when(user_service.view_profile(1L)).thenReturn(user);
        mockMvc.perform(get("/users/1/profile", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.user_id").value(1));
    }

    @Test
    public void should_return_user_not_found() throws Exception {
        when(user_service.view_profile(1L)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario no existe"));
        mockMvc.perform(get("/users/1/profile", 1))
                .andExpect(status().isNotFound());
    }

    //endregion

    //region Update profile
    @Test
    public void should_update_user() throws Exception {
        // Creem el user dto que enviarem com a Request Body
        UserDto user_dto = new UserDto();
        user_dto.setUser_id(1L);
        user_dto.setEmail("test@test.com");
        user_dto.setUsername("test");
        user_dto.setReal_name("test");
        user_dto.setBirth_date("12/12/2002");

        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user_dto);

        // User Model que rebrem del servei
        UserModel expected_user_model = new UserModel();
        expected_user_model.setUser_id(1L);
        expected_user_model.setEmail("test@test.com");
        expected_user_model.setUsername("test");
        expected_user_model.setReal_name("test");
        expected_user_model.setBirth_date("12/12/2002");

        // Fem que el servei retorni el user model creat previament
        when(user_service.update_user(any(UserModel.class))).thenReturn(expected_user_model);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/profile/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.user_id").value(1))
                .andExpect(jsonPath("$.email").value("test@test.com"))
                .andExpect(jsonPath("$.username").value("test"))
                .andExpect(jsonPath("$.real_name").value("test"))
                .andExpect(jsonPath("$.birth_date").value("12/12/2002"));
    }

    @Test
    public void should_return_user_deleted() throws Exception{
        // Creem el user dto que enviarem com a Request Body
        UserDto user_dto = new UserDto();
        user_dto.setUser_id(1L);
        user_dto.setEmail("test@test.com");
        user_dto.setUsername("test");
        user_dto.setReal_name("test");
        user_dto.setBirth_date("12/12/2002");

        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user_dto);

        // Fem que el servei retorni el user model creat previament
        when(user_service.update_user(any(UserModel.class))).thenThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "El usuario esta eliminado"));

        mockMvc.perform(MockMvcRequestBuilders.put("/users/profile/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isInternalServerError());
    }
    //endregion

    //region Delete
    @Test
    public void should_delete_user() throws Exception {
        Long userId = 6L;
        Mockito.doNothing().when(user_service).delete_user(userId);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/delete/{user_Id}", userId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    //endregion

    //region Favorite chargers
    @Test
    public void should_return_favourite_chargers() throws Exception {
        Long user_id = 10L;

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

        when(user_service.get_chargers_favorites(user_id)).thenReturn(expected_chargers);

        mockMvc.perform(get("/users/{user_id}/favorites", user_id))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id_charger").value(1))
                .andExpect(jsonPath("$[0].latitude").value(1.0))
                .andExpect(jsonPath("$[0].longitude").value(1.0))
                .andExpect(jsonPath("$[0].is_public").value(false))
                .andExpect(jsonPath("$[1].id_charger").value(2))
                .andExpect(jsonPath("$[1].latitude").value(2.0))
                .andExpect(jsonPath("$[1].longitude").value(2.0))
                .andExpect(jsonPath("$[1].is_public").value(false));
    }

    @Test
    public void should_add_new_favorite() throws Exception {
        Long user_id = 10L;
        Long charger_id = 1L;

        // Charger que retornarem
        ChargerModel expected_charger = new ChargerModel();
        expected_charger.setId_charger(1L);
        expected_charger.setLatitude(1.0);
        expected_charger.setLongitude(1.0);
        expected_charger.setIs_public(false);
        ArrayList<ChargerModel> expected_chargers = new ArrayList<>();
        expected_chargers.add(expected_charger);


        Mockito.doNothing().when(user_service).add_favorite(user_id, charger_id);

        mockMvc.perform(put("/users/{user_id}/addfavorite/{charger_id}", user_id, charger_id))
                .andExpect(status().isOk());

        when(user_service.get_chargers_favorites(user_id)).thenReturn(expected_chargers);

        mockMvc.perform(get("/users/{user_id}/favorites", user_id))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id_charger").value(1))
                .andExpect(jsonPath("$[0].latitude").value(1.0))
                .andExpect(jsonPath("$[0].longitude").value(1.0))
                .andExpect(jsonPath("$[0].is_public").value(false));
    }

    @Test
    public void should_delete_favorite() throws Exception {
        Long user_id = 10L;
        Long charger_id = 1L;

        // Charger que retornarem
        ArrayList<ChargerModel> expected_chargers = new ArrayList<>();

        Mockito.doNothing().when(user_service).delete_favorite(user_id, charger_id);

        mockMvc.perform(put("/users/{user_id}/deletefavorite/{charger_id}", user_id, charger_id))
                .andExpect(status().isOk());

        when(user_service.get_chargers_favorites(user_id)).thenReturn(expected_chargers);

        mockMvc.perform(get("/users/{user_id}/favorites", user_id))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(0));
    }
    //endregion
}
