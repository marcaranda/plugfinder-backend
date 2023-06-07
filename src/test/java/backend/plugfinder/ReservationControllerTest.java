package backend.plugfinder;

import backend.plugfinder.controllers.ChargeController;
import backend.plugfinder.controllers.ReservationController;
import backend.plugfinder.helpers.CarId;

import backend.plugfinder.services.ChargerService;
import backend.plugfinder.services.ReservationService;
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
@WebMvcTest(ReservationController.class)
public class ReservationControllerTest extends AbstractBaseControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ReservationService reservation_service;

    //region Get Methods
    @Test
    public void should_return_reservations() throws Exception {
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


        ReservationModel reservation = new ReservationModel();
        reservation.set_id_reservation(1L);
        reservation.setCharger(charger1);
        reservation.setCar(car_model);

        ReservationModel reservation2 = new ReservationModel();
        reservation2.set_id_reservation(1L);
        reservation2.setCharger(charger1);
        reservation2.setCar(car_model);


        ArrayList<ReservationModel> expected = new ArrayList<>();
        expected.add(reservation);
        expected.add(reservation2);

        // Mock the service to return the expected charges
        when(reservation_service.get_reservations(null)).thenReturn(expected);

        mockMvc.perform(MockMvcRequestBuilders.get("/reservations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0]._id_reservation").value(1));
    }



    @Test
    public void should_return_reservation() throws Exception {

        ArrayList<ReservationModel> expected = new ArrayList<>();

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

        expected.add(reservation);

        // Mock the service to return the expected charges
        when(reservation_service.get_reservations(1L)).thenReturn(expected);

        mockMvc.perform(get("/reservations?id=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0]._id_reservation").value(1L));
    }



}