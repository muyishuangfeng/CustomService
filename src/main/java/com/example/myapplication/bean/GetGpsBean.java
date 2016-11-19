package com.example.myapplication.bean;

/**
 * Created by Silence on 2016/11/18.
 */

public class GetGpsBean {
    //详细地址
    public String address;
    //城市名
    public String cityName;
    private double Longitude;
    private double Latitude;

    public String getAddress() {
        return address;
    }

    public String getCityName() {
        return cityName;
    }

    public double getLongitude() {
        return Longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }
}
