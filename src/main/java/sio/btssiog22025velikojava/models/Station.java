package sio.btssiog22025velikojava.models;

import java.util.Date;
import java.util.List;

public class Station {

    private String station_id;
    private String name;
    private String capacity;
    private double lat;
    private double lon;
    private int num_mechanical_bikes_available;
    private int num_electric_bikes_available;
    private Date last_upate_date;

    public Station(String station_id, String name, String capacity, double lat, double lon, int num_mechanical_bikes_available, int num_electric_bikes_available, Date last_upate_date) {
        this.station_id = station_id;
        this.name = name;
        this.capacity = capacity;
        this.lat = lat;
        this.lon = lon;
        this.num_mechanical_bikes_available = num_mechanical_bikes_available;
        this.num_electric_bikes_available = num_electric_bikes_available;
        this.last_upate_date = last_upate_date;
    }

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getNum_mechanical_bikes_available() {
        return num_mechanical_bikes_available;
    }

    public void setNum_mechanical_bikes_available(int num_mechanical_bikes_available) {
        this.num_mechanical_bikes_available = num_mechanical_bikes_available;
    }

    public int getNum_electric_bikes_available() {
        return num_electric_bikes_available;
    }

    public void setNum_electric_bikes_available(int num_electric_bikes_available) {
        this.num_electric_bikes_available = num_electric_bikes_available;
    }

    public Date getLast_upate_date() {
        return last_upate_date;
    }

    public void setLast_upate_date(Date last_upate_date) {
        this.last_upate_date = last_upate_date;
    }
}