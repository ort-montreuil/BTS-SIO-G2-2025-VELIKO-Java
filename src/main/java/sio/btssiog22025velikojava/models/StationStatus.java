package sio.btssiog22025velikojava.models;

import java.util.List;

public class StationStatus {
    private String station_id;
    private int num_bikes_available;
    private List<BikeType> num_bikes_available_types;

    public static class BikeType {
        private int mechanical;
        private int ebike;

        public int getMechanical() {
            return mechanical;
        }

        public int getEbike() {
            return ebike;
        }

        public void setMechanical(int mechanical) {
            this.mechanical = mechanical;
        }

        public void setEbike(int ebike) {
            this.ebike = ebike;
        }
    }

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public List<BikeType> getNum_bikes_available_types() {
        return num_bikes_available_types;
    }

    public void setNum_bikes_available_types(List<BikeType> num_bikes_available_types) {
        this.num_bikes_available_types = num_bikes_available_types;
    }
    public int getNum_bikes_available() {
        return num_bikes_available;
    }

    public void setNum_bikes_available(int num_bikes_available) {
        this.num_bikes_available = num_bikes_available;
    }
}
