package sio.btssiog22025velikojava.controllers;

import sio.btssiog22025velikojava.models.StationFav;
import sio.btssiog22025velikojava.services.StationFavService;
import sio.btssiog22025velikojava.services.StationService;

import java.sql.SQLException;
import java.util.ArrayList;

public class StationFavController {
    private final StationFavService stationFavService;

    public StationFavController() {
        this.stationFavService = new StationFavService();
    }
    public int getFavCountById(long stationId) throws SQLException {
        return stationFavService.getFavCountById(stationId);
    }
    public ArrayList<StationFav>allFavStation() throws SQLException {
        return stationFavService.allFavStation();
    }
}
