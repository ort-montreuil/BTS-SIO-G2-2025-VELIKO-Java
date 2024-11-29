package sio.btssiog22025velikojava.controllers;

import sio.btssiog22025velikojava.models.Station;
import sio.btssiog22025velikojava.services.StationService;

import java.sql.SQLException;
import java.util.ArrayList;

public class StationController {

    private StationService stationService;

    public StationController() {
        this.stationService = new StationService();
    }

    public ArrayList<Station> allStation() throws SQLException {
        return stationService.allStation();
    }
}
