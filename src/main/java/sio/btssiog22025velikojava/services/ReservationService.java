package sio.btssiog22025velikojava.services;

import sio.btssiog22025velikojava.models.Reservation;
import sio.btssiog22025velikojava.repositories.ReservationRepository;

import java.util.ArrayList;
import java.util.Map;

public class ReservationService {

    private ReservationRepository reservationRepository;

    public ReservationService() {
        this.reservationRepository = new ReservationRepository();
    }

    public ArrayList<Reservation> allReservation() {
        return reservationRepository.allReservation();
    }

    public ArrayList<Map<String, Object>> getReservationsByMonthAndType() {
        return reservationRepository.getReservationsByMonthAndType();
    }
}
