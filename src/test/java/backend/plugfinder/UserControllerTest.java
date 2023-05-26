package backend.plugfinder;

import backend.plugfinder.controllers.UserController;
import backend.plugfinder.controllers.dto.UserDto;
import backend.plugfinder.helpers.OurException;
import backend.plugfinder.services.models.UserModel;
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

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    //endregion
}
