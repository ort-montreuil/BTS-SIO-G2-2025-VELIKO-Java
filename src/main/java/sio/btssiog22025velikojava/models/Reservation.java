package sio.btssiog22025velikojava.models;

import java.util.Date;

public class Reservation {
    private int reservation_id;
    private Date reservation_date;
    private int id_station_depart;
    private int id_station_arrive;
    private String email_user;
    private String type_bike;

    public Reservation(int reservation_id, Date reservation_date, int id_station_depart, int id_station_arrive, String email_user, String type_bike) {
        this.reservation_id = reservation_id;
        this.reservation_date = reservation_date;
        this.id_station_depart = id_station_depart;
        this.id_station_arrive = id_station_arrive;
        this.email_user = email_user;
        this.type_bike = type_bike;
    }

    public int getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
    }

    public Date getReservation_date() {
        return reservation_date;
    }

    public void setReservation_date(Date reservation_date) {
        this.reservation_date = reservation_date;
    }

    public int getId_station_depart() {
        return id_station_depart;
    }

    public void setId_station_depart(int id_station_depart) {
        this.id_station_depart = id_station_depart;
    }

    public int getId_station_arrive() {
        return id_station_arrive;
    }

    public void setId_station_arrive(int id_station_arrive) {
        this.id_station_arrive = id_station_arrive;
    }

    public String getEmail_user() {
        return email_user;
    }

    public void setEmail_user(String email_user) {
        this.email_user = email_user;
    }

    public String getType_bike() {
        return type_bike;
    }

    public void setType_bike(String type_bike) {
        this.type_bike = type_bike;
    }
}
