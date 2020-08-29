package com.example.yogeshwar.rentalplatform;

public class shopdetails {
    private double lat;
    private double lg;
    private String nam;

    public shopdetails() {
    }

    public shopdetails(double lat, double lg, String nam) {

        this.lat = lat;
        this.lg = lg;
        this.nam = nam;
    }


    public double getLat() {
        return lat;
    }

    public double getLg() {
        return lg;
    }

    public String getNam() {
        return nam;
    }


    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setNam(String nam) {
        this.nam = nam;
    }
    public void setLg(double lg) {
        this.lg = lg;
    }

    @Override
    public String toString() {
        return "shopdetails{" +
                "lat=" + lat +
                ", lg=" + lg +
                ", nam='" + nam + '\'' +
                '}';
    }
}
