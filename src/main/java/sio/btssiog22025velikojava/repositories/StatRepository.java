package sio.btssiog22025velikojava.repositories;

import sio.btssiog22025velikojava.tools.DataSourceProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

}
