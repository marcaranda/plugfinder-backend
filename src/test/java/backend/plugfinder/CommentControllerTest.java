package backend.plugfinder;

import backend.plugfinder.controllers.CommentController;
import backend.plugfinder.controllers.dto.ChargerDto;
import backend.plugfinder.controllers.dto.CommentDto;
import backend.plugfinder.controllers.dto.UserDto;
import backend.plugfinder.helpers.OurException;
import backend.plugfinder.services.models.ChargerModel;
import backend.plugfinder.services.models.CommentModel;
import backend.plugfinder.services.models.UserModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import backend.plugfinder.services.CommentService;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(CommentController.class)
public class CommentControllerTest extends AbstractBaseControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CommentService comment_service;

    //region Get methods

    @Test
    public void get_charger_comments() throws Exception {
        //region Definición de datos de prueba
        ArrayList<CommentModel> comments = new ArrayList<>();
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


        CommentModel comment = new CommentModel();
        comment.setComment("First comment");
        comment.setUser(userModel);
        comment.setCharger(chargerModel);
        comment.setPoints(2.7);
        comments.add(comment);

        CommentModel comment2 = new CommentModel();
        comment2.setComment("Second comment");
        comment2.setUser(userModel);
        comment2.setCharger(chargerModel);
        comment2.setPoints(4.1);
        comments.add(comment2);
        //endregion

        when(comment_service.get_charger_comments(1L)).thenReturn(comments);

        // Realizar la solicitud GET a la ruta "/charger/{charger_id}"
        mockMvc.perform(get("/comment/charger/{charger_id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].comment").value("First comment"))
                .andExpect(jsonPath("$[0].user.user_id").value(1))
                .andExpect(jsonPath("$[0].charger.id_charger").value(1))
                .andExpect(jsonPath("$[0].points").value(2.7))
                .andExpect(jsonPath("$[1].comment").value("Second comment"))
                .andExpect(jsonPath("$[1].user.user_id").value(1))
                .andExpect(jsonPath("$[1].charger.id_charger").value(1))
                .andExpect(jsonPath("$[1].points").value(4.1));
    }

    @Test
    public void get_user_comments() throws Exception {
        //region Definición de datos de prueba
        ArrayList<CommentModel> comments = new ArrayList<>();
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


        CommentModel comment = new CommentModel();
        comment.setComment("First comment");
        comment.setUser(userModel);
        comment.setCharger(chargerModel);
        comment.setPoints(2.7);
        comments.add(comment);

        CommentModel comment2 = new CommentModel();
        comment2.setComment("Second comment");
        comment2.setUser(userModel);
        comment2.setCharger(chargerModel);
        comment2.setPoints(4.1);
        comments.add(comment2);
        //endregion

        when(comment_service.get_user_comments(1L)).thenReturn(comments);
        when(comment_service.get_user_comments(2L)).thenReturn(new ArrayList<>());

        // Realizar la solicitud GET a la ruta "/charger/{charger_id}"
        mockMvc.perform(get("/comment/user/{user_id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].comment").value("First comment"))
                .andExpect(jsonPath("$[0].user.user_id").value(1))
                .andExpect(jsonPath("$[0].charger.id_charger").value(1))
                .andExpect(jsonPath("$[0].points").value(2.7))
                .andExpect(jsonPath("$[1].comment").value("Second comment"))
                .andExpect(jsonPath("$[1].user.user_id").value(1))
                .andExpect(jsonPath("$[1].charger.id_charger").value(1))
                .andExpect(jsonPath("$[1].points").value(4.1));

        mockMvc.perform(get("/comment/user/{user_id}", 2L))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(0));
    }
    //endregion

    //region Post methods

    @Test
    public void post_comment() throws Exception {
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

        CommentDto comment = new CommentDto();
        comment.setComment("First comment");
        comment.setUser(user);
        comment.setCharger(charger);
        comment.setPoints(4.7);

        ObjectMapper objectMapper = new ObjectMapper();
        String commentJson = objectMapper.writeValueAsString(comment);

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


        CommentModel commentModel = new CommentModel();
        commentModel.setComment_id(1L);
        commentModel.setComment("First comment");
        commentModel.setUser(userModel);
        commentModel.setCharger(chargerModel);
        commentModel.setPoints(4.7);

        when(comment_service.save_comment(any(CommentModel.class))).thenReturn(commentModel);
        //endregion

        // Realizar la solicitud POST a la ruta "/comment"
        mockMvc.perform(MockMvcRequestBuilders.post("/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commentJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.comment_id").value(1))
                .andExpect(jsonPath("$.comment").value("First comment"))
                .andExpect(jsonPath("$.user.user_id").value(1))
                .andExpect(jsonPath("$.charger.id_charger").value(1))
                .andExpect(jsonPath("$.points").value(4.7));
    }

    //endregion

    //region Put methods

    @Test
    public void should_edit_comment() throws Exception {
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

        CommentDto comment = new CommentDto();
        comment.setComment("Comment edited");
        comment.setUser(user);
        comment.setCharger(charger);
        comment.setPoints(2.7);

        ObjectMapper objectMapper = new ObjectMapper();
        String commentJson = objectMapper.writeValueAsString(comment);

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


        CommentModel commentModel = new CommentModel();
        commentModel.setComment_id(1L);
        commentModel.setComment("Comment edited");
        commentModel.setUser(userModel);
        commentModel.setCharger(chargerModel);
        commentModel.setPoints(2.7);

        //endregion

        when(comment_service.update_comment(any(CommentModel.class))).thenReturn(commentModel);

        // Realizar la solicitud POST a la ruta "/comment"
        mockMvc.perform(MockMvcRequestBuilders.put("/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(commentJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.comment_id").value(1))
                .andExpect(jsonPath("$.comment").value("Comment edited"))
                .andExpect(jsonPath("$.user.user_id").value(1))
                .andExpect(jsonPath("$.charger.id_charger").value(1))
                .andExpect(jsonPath("$.points").value(2.7));
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

        CommentDto comment = new CommentDto();
        comment.setComment("Comment edited");
        comment.setUser(user);
        comment.setCharger(charger);
        comment.setPoints(2.7);

        ObjectMapper objectMapper = new ObjectMapper();
        String commentJson = objectMapper.writeValueAsString(comment);

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


        CommentModel commentModel = new CommentModel();
        commentModel.setComment_id(1L);
        commentModel.setComment("Comment edited");
        commentModel.setUser(userModel);
        commentModel.setCharger(chargerModel);
        commentModel.setPoints(2.7);

        //endregion

        when(comment_service.update_comment(any(CommentModel.class))).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "El comentario no existe."));

        // Realizar la solicitud POST a la ruta "/comment"
        mockMvc.perform(MockMvcRequestBuilders.put("/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(commentJson))
                .andExpect(status().isNotFound());
    }
    //endregion

    //region Delete methods

    @Test
    public void should_delete_comment() throws Exception {
        Long commentId = 1L;
        when(comment_service.delete_comment(commentId)).thenReturn(new ResponseEntity<String>("Comentario eliminado", HttpStatus.OK));

        mockMvc.perform(delete("/comment/{commentId}/delete", commentId))
                .andExpect(status().isOk())
                .andExpect(content().string("Comentario eliminado"));
    }

    @Test
    public void should_return_comment_not_found_delete() throws Exception {
        Long commentId = 1L;
        when(comment_service.delete_comment(commentId)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "El comentario no existe."));

        mockMvc.perform(delete("/comment/{commentId}/delete", commentId))
                .andExpect(status().isNotFound());
    }
    //endregion
}
