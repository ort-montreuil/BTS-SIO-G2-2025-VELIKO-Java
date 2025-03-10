package sio.btssiog22025velikojava.controllers;

import sio.btssiog22025velikojava.services.StatService;

import java.lang.invoke.StringConcatFactory;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StatController {
    private StatService statService;

    public StatController() {
        this.statService = new StatService();
    }

    public int countStation() throws SQLException {
        return statService.countStation();
    }

    public int countBikes() throws SQLException {
        return statService.countBikes();
    }

    public int countMechanicalBikes() throws SQLException {
        return statService.countMechanicalBikes();
    }

    public int countElectricBikes() throws SQLException {
        return statService.countElectricBikes();
    }

    public int countFavoriteStation() throws SQLException {
        return statService.countFavoriteStation();
    }

    public int countReservations() throws SQLException {
        return statService.countReservations();
    }

    public Map<String, Integer> getReservationsByStationDepart() throws SQLException {
        return statService.getReservationsByStationDepart();
    }

    public Map<String, Integer> getReservationsByStationArrive() throws SQLException {
        return statService.getReservationsByStationArrive();
    }

    public Integer countUsers() throws SQLException {
        return statService.countUsers();
    }
    public String getUserwithMostFavorite() throws SQLException {
        return statService.getUserwithMostFavorite();
    }
    public String getUserwithMostReservation() throws SQLException {
        return statService.getUserwithMostReservation();
    }

    public ArrayList<Map<String,Object>> getTopUserCities() throws SQLException {
        return statService.getTopUserCities();
    }

}
