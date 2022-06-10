package com.example.kykyemekuygulama;

public class City implements Comparable<City> {
    private int cityID;
    private String cityName;


    public City(int cityID, String cityName) {
        this.cityID = cityID;
        this.cityName = cityName;
    }

    public int getCityID() {
        return cityID;
    }

    public String getCityName() {
        return cityName;
    }

    @Override
    public String toString() {
        return cityName;
    }


    @Override
    public int compareTo(City another) {
        return this.getCityID() - another.getCityID();//ascending order
//            return another.getCountryID()-this.getCountryID();//descending  order
    }
}
