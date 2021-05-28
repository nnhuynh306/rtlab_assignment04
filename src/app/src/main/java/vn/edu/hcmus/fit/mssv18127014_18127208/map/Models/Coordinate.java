package vn.edu.hcmus.fit.mssv18127014_18127208.map.Models;

public class Coordinate {
    private double latitude;
    private double longitude;
    private String title;

    public Coordinate(double latitude, double longitude, String title) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
