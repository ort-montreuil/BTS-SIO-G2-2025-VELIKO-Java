package sio.btssiog22025velikojava.services;

import sio.btssiog22025velikojava.repositories.StatRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatService {
    private final StatRepository statRepository;

    public StatService() {
        this.statRepository = new StatRepository();
    }

    public int countStation() throws SQLException {
        return statRepository.countStation();
    }

    public int countBikes() throws SQLException {
        return statRepository.countBikes();
    }

    public int countMechanicalBikes() throws SQLException {
        return statRepository.countMechanicalBikes();
    }

    public int countElectricBikes() throws SQLException {
        return statRepository.countElectricBikes();
    }

    public int countFavoriteStation() throws SQLException {
        return statRepository.countFavoriteStation();
    }

    public int countReservations() throws SQLException {
        return statRepository.countReservations();
    }

    public Map<String, Integer> getReservationsByStationDepart() throws SQLException {
        return statRepository.getReservationsByStationDepart();
    }

    public Map<String, Integer> getReservationsByStationArrive() throws SQLException {
        return statRepository.getReservationsByStationArrive();
    }
    public Integer countUsers() throws SQLException {
        return statRepository.countUsers();
    }
    public String getUserwithMostFavorite() throws SQLException {
        return statRepository.getUserWithMostFavorite();
    }
    public String getUserwithMostReservation() throws SQLException {
        return statRepository.getUserWithMostReservation();
    }
    public ArrayList<Map<String,Object>> getTopUserCities() throws SQLException {
        return statRepository.getTopUserCities();
    }

}
