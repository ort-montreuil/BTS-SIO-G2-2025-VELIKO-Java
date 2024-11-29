package sio.btssiog22025velikojava.services;

import sio.btssiog22025velikojava.models.Station;
import sio.btssiog22025velikojava.repositories.StationRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class StationService {
    private final StationRepository stationRepository;

    public StationService() {
        this.stationRepository = new StationRepository();
    }

    public ArrayList<Station> allStation() throws SQLException {
        return stationRepository.allStation();
    }
}
