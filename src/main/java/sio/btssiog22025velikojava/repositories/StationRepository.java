package sio.btssiog22025velikojava.repositories;


import sio.btssiog22025velikojava.models.Station;
import sio.btssiog22025velikojava.tools.DataSourceProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StationRepository {
    private Connection connection;

    public StationRepository() {
        this.connection = DataSourceProvider.getCnx();
    }

    public ArrayList<Station> allStation() throws SQLException {
        ArrayList<Station> stations = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement("SELECT station.station_id, station.name, station.capacity, station.lat, station.lon, station.mechanical_bikes, station.electric_bikes, station.last_updated_at FROM `station`;");
        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            Station station = new Station(rs.getLong(1),rs.getString(2),rs.getString(3),rs.getDouble(4),rs.getDouble(5),rs.getInt(6),rs.getInt(7),rs.getDate(8));
            stations.add(station);
        }
        return stations;
    }
}