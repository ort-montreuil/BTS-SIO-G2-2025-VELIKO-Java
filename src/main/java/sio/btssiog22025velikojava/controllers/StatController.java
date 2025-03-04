package sio.btssiog22025velikojava.controllers;

import sio.btssiog22025velikojava.services.StatService;

import java.sql.SQLException;

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

}
