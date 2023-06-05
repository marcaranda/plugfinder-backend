package backend.plugfinder;

import backend.plugfinder.controllers.BrandController;
import backend.plugfinder.controllers.dto.BrandDto;
import backend.plugfinder.services.models.BrandModel;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(BrandController.class)
public class BrandControllerTest extends AbstractBaseControllerTest{
    @Autowired
    MockMvc mockMvc;

    @Test
    public void should_save_brand() throws Exception {
        // Creem el brand dto que enviarem com a Request Body
        BrandDto brand_dto = new BrandDto();
        brand_dto.setName("test");
        brand_dto.setKnown(true);

        ObjectMapper objectMapper = new ObjectMapper();
        String brandJson = objectMapper.writeValueAsString(brand_dto);

        // Brand Model que rebrem del servei
        BrandModel expected_brand_model = new BrandModel();
        expected_brand_model.setName("test");
        expected_brand_model.setKnown(true);

        // Fem que el servei retorni el brand model creat previament
        when(brand_service.save_brand(any(BrandModel.class))).thenReturn(expected_brand_model);

        mockMvc.perform(MockMvcRequestBuilders.post("/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brandJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.known").value(true));

        // Verifiqum que s'ha cridat al metode de save amb la brand correcte
        verify(brand_service).save_brand(argThat(brandModel -> {
            return brandModel.getName().equals("test") &&
                    brandModel.isKnown() == true;
        }));
    }













