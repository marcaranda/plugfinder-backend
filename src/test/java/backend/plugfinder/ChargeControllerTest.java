package backend.plugfinder;

import backend.plugfinder.controllers.ChargeController;
import backend.plugfinder.helpers.CarId;
import backend.plugfinder.services.ChargeService;
import backend.plugfinder.services.models.CarModel;
import backend.plugfinder.services.models.ChargeModel;
import backend.plugfinder.services.models.ChargerModel;
import backend.plugfinder.services.models.ReservationModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.ArrayList;


import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ChargeController.class)
public class ChargeControllerTest extends AbstractBaseControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ChargeService charge_service;


    //region Get Methods
    @Test
    public void should_return_charges() throws Exception {
        // Charge Model that will be returned by the service

        ChargerModel charger1 = new ChargerModel();
        charger1.setId_charger(1L);
        charger1.setLatitude(1.0);
        charger1.setLongitude(1.0);
        charger1.setIs_public(false);

        ChargerModel charger2 = new ChargerModel();
        charger2.setId_charger(2L);
        charger2.setLatitude(2.0);
        charger2.setLongitude(2.0);
        charger2.setIs_public(false);

        CarModel car_model = new CarModel();
        car_model.setId(new CarId("9999TTT", 1L));

        CarModel car_model2 = new CarModel();
        car_model2.setId(new CarId("9999TTT", 1L));


        ChargeModel carga = new ChargeModel();
        carga.setId_charge(1L);
        carga.setCharger(charger1);
        carga.setCar(car_model);

        ChargeModel carga2 = new ChargeModel();
        carga2.setId_charge(2L);
        carga2.setCharger(charger2);
        carga2.setCar(car_model2);


        ArrayList<ChargeModel> expectedCharges = new ArrayList<>();
        expectedCharges.add(carga);
        expectedCharges.add(carga2);

        // Mock the service to return the expected charges
        when(charge_service.get_charges(null)).thenReturn(expectedCharges);

        mockMvc.perform(MockMvcRequestBuilders.get("/charges")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id_charge").value(1))
                .andExpect(jsonPath("$[1].id_charge").value(2));
    }



    @Test
    public void should_return_charge() throws Exception {

        ArrayList<ChargeModel> expectedCharges = new ArrayList<>();

        ChargerModel charger1 = new ChargerModel();
        charger1.setId_charger(1L);
        charger1.setLatitude(1.0);
        charger1.setLongitude(1.0);
        charger1.setIs_public(false);

        CarModel car_model = new CarModel();
        car_model.setId(new CarId("9999TTT", 1L));

        ChargeModel carga = new ChargeModel();
        carga.setId_charge(1L);
        carga.setCharger(charger1);
        carga.setCar(car_model);

        expectedCharges.add(carga);

        // Mock the service to return the expected charges
        when(charge_service.get_charges(1L)).thenReturn(expectedCharges);

        mockMvc.perform(get("/charges?id=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].id_charge").value(1L));
    }


    @Test
    public void should_create_charge_from_reservation() throws Exception {
        ChargerModel charger1 = new ChargerModel();
        charger1.setId_charger(1L);
        charger1.setLatitude(1.0);
        charger1.setLongitude(1.0);
        charger1.setIs_public(false);

        CarModel car_model = new CarModel();
        car_model.setId(new CarId("9999TTT", 1L));

        ReservationModel reservation = new ReservationModel();
        reservation.set_id_reservation(1L);
        reservation.setCharger(charger1);
        reservation.setCar(car_model);

        ChargeModel carga = new ChargeModel();

        when(charge_service.save_charge_from_reservation(1L)).thenReturn(carga);

        mockMvc.perform(MockMvcRequestBuilders.post("/charges/new_from_reservation?reservation_id=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id_charge").value(0));
    }


}