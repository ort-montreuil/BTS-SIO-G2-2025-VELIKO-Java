package sio.btssiog22025velikojava.models;

public class StationFav {

    private int id;
    private String email;
    private long station_id;

    public StationFav(int id, String email, long station_id) {
        this.id = id;
        this.email = email;
        this.station_id = station_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getStation_id() {
        return station_id;
    }

    public void setStation_id(long station_id) {
        this.station_id = station_id;
    }
}
