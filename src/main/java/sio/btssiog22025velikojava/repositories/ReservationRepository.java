package sio.btssiog22025velikojava.repositories;

import sio.btssiog22025velikojava.models.Reservation;
import sio.btssiog22025velikojava.tools.DataSourceProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReservationRepository {

    private Connection connection;

    public ReservationRepository() {
        this.connection = DataSourceProvider.getCnx();
    }

    public ArrayList<Reservation> allReservation() {
        ArrayList<Reservation> reservations = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT reservation.id, reservation.date_reservation, reservation.id_station_depart, reservation.id_station_fin, reservation.user_email, reservation.type_velo FROM reservation;");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Reservation reservation = new Reservation(rs.getInt(1), rs.getDate(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6));
                reservations.add(reservation);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }
    public ArrayList<Map<String, Object>> getReservationsByMonthAndType() {
        ArrayList<Map<String, Object>> data = new ArrayList<>();

        try {
            String query = "SELECT EXTRACT(MONTH FROM reservation.date_reservation) AS month, reservation.type_velo, COUNT(*) AS reservations_count " +
                    "FROM reservation " +
                    "GROUP BY month, reservation.type_velo " +
                    "ORDER BY month;";

            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            // Parcourir les résultats et les ajouter à la liste
            while (rs.next()) {
                Map<String, Object> entry = new HashMap<>();
                entry.put("month", rs.getInt("month"));
                entry.put("bikeType", rs.getString("type_velo"));
                entry.put("reservations", rs.getInt("reservations_count"));
                data.add(entry);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

}
