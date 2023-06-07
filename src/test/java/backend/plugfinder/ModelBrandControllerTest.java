package backend.plugfinder;

import backend.plugfinder.controllers.ModelBrandController;
import backend.plugfinder.controllers.dto.ModelBrandDto;
import backend.plugfinder.helpers.ModelBrandId;
import backend.plugfinder.services.models.ModelBrandModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ModelBrandController.class)
public class ModelBrandControllerTest extends AbstractBaseControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void should_register_model_brand() throws Exception {
        // Creem el model brand dto que enviarem com a Request Body
        ModelBrandDto model_brand_dto = new ModelBrandDto();
        model_brand_dto.setId(new ModelBrandId("test", "test"));
        model_brand_dto.setKnown(true);

        ObjectMapper objectMapper = new ObjectMapper();
        String modelJson = objectMapper.writeValueAsString(model_brand_dto);

        // Model Brand Model que rebrem del servei
        ModelBrandModel expected_model_brand_model = new ModelBrandModel();
        expected_model_brand_model.setId(new ModelBrandId("test", "test"));
        expected_model_brand_model.setKnown(true);

        // Fem que el servei retorni el model brand model creat previament
        when(model_brand_service.save_model(any(ModelBrandModel.class))).thenReturn(expected_model_brand_model);

        mockMvc.perform(MockMvcRequestBuilders.post("/models")
                .contentType(MediaType.APPLICATION_JSON)
                .content(modelJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id.name").value("test"))
                .andExpect(jsonPath("$.id.brand_name").value("test"))
                .andExpect(jsonPath("$.known").value(true));
    }
}
