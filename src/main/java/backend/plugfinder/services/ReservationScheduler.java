
package backend.plugfinder.services;

import backend.plugfinder.helpers.OurException;
import backend.plugfinder.services.ReservationService;
import backend.plugfinder.services.models.ReservationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ReservationScheduler {

    @Autowired
    ReservationService reservationService;

    @Scheduled(fixedDelay = 1000 * 60) // Run every minute
    public void checkAndCompleteReservations() throws OurException {
        ArrayList<ReservationModel> reservations =  reservationService.get_reservations(null);

        for (ReservationModel reservation : reservations) {
            if (reservation.getEnded_at() == null && reservation.timeExtended()) {
                reservationService.end_reservation(reservation);
            }
        }
    }
}