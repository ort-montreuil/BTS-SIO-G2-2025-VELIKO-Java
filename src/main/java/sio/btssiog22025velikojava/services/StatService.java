package sio.btssiog22025velikojava.services;

import sio.btssiog22025velikojava.repositories.StatRepository;

import java.sql.SQLException;
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

}
