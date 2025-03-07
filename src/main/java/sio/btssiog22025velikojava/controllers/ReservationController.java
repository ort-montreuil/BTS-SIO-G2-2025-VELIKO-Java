package sio.btssiog22025velikojava.controllers;

import sio.btssiog22025velikojava.models.Reservation;
import sio.btssiog22025velikojava.services.ReservationService;

import java.util.ArrayList;
import java.util.Map;

public class ReservationController {
    private ReservationService reservationService;

    public ReservationController() {
        this.reservationService = new ReservationService();
    }

    public ArrayList<Reservation> allReservation() {
        return reservationService.allReservation();
    }

    public ArrayList<Map<String, Object>> getReservationsByMonthAndType() {
        return reservationService.getReservationsByMonthAndType();
    }
}
