package sio.btssiog22025velikojava.repositories;

import sio.btssiog22025velikojava.models.StationFav;
import sio.btssiog22025velikojava.tools.DataSourceProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StationFavRepository {
    private Connection connection;

    public StationFavRepository() {
        this.connection = DataSourceProvider.getCnx();
    }

    //non utilisé
    public int getFavCountById(long stationId) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM `station_fav` WHERE station_id = ?;");
        ps.setLong(1, stationId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    //récupérer tous les favoris
    public ArrayList<StationFav> allStationFav() throws SQLException {
        ArrayList<StationFav> stationFavs = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement("SELECT station_fav.id, station_fav.user_email, station_fav.station_id FROM `station_fav`;");
        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            StationFav stationFav = new StationFav(rs.getInt(1),rs.getString(2),rs.getLong(3));
            stationFavs.add(stationFav);
        }
        return stationFavs;
    }
}
