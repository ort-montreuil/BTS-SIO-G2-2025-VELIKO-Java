package sio.btssiog22025velikojava.repositories;

import sio.btssiog22025velikojava.tools.DataSourceProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class StatRepository {
    private Connection connection;

    public StatRepository() {
        this.connection = DataSourceProvider.getCnx();
    }
    public int countStation() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM station;");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        rs.close();
        ps.close();
        return count;
    }
    public int countBikes() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT SUM(mechanical_bikes + electric_bikes) FROM station;");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        rs.close();
        ps.close();
        return count;
    }

    public int countMechanicalBikes() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT SUM(mechanical_bikes) FROM station;");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        rs.close();
        ps.close();
        return count;
    }
    public int countElectricBikes() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT SUM(electric_bikes) FROM station;");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        rs.close();
        ps.close();
        return count;
    }
    public int countFavoriteStation() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM station_fav");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        rs.close();
        ps.close();
        return count;
    }
    public int countReservations() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM reservation");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        rs.close();
        ps.close();
        return count;
    }

    public Map<String, Integer> getReservationsByStationDepart() throws SQLException {
        Map<String, Integer> reservationsByStation = new HashMap<>();

        String query = "SELECT station.name, COUNT(reservation.id_station_depart) AS reservation_count FROM station LEFT JOIN reservation ON station.station_id = reservation.id_station_depart GROUP BY station.name ORDER BY reservation_count DESC LIMIT 10;";

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            reservationsByStation.put(rs.getString("name"), rs.getInt("reservation_count"));
        }

        rs.close();
        ps.close();

        return reservationsByStation;
    }
    public Map<String, Integer> getReservationsByStationArrive() throws SQLException {
        Map<String, Integer> reservationsByStation = new HashMap<>();

        String query = "SELECT station.name, COUNT(reservation.id_station_fin) AS reservation_count FROM station LEFT JOIN reservation ON station.station_id = reservation.id_station_fin GROUP BY station.name ORDER BY reservation_count DESC LIMIT 10;";

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            reservationsByStation.put(rs.getString("name"), rs.getInt("reservation_count"));
        }

        rs.close();
        ps.close();

        return reservationsByStation;
    }


}
