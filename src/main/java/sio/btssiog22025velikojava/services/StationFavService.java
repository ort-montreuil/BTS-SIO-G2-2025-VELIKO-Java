package sio.btssiog22025velikojava.services;

import sio.btssiog22025velikojava.models.StationFav;
import sio.btssiog22025velikojava.repositories.StationFavRepository;
import sio.btssiog22025velikojava.repositories.StationRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class StationFavService {
    private final StationFavRepository stationFavRepository;

    public StationFavService() {
        this.stationFavRepository = new StationFavRepository();
    }
    public int getFavCountById(long stationId) throws SQLException {
        return stationFavRepository.getFavCountById(stationId);
    }
    public ArrayList<StationFav>allFavStation() throws SQLException {
        return stationFavRepository.allStationFav();
    }
}
