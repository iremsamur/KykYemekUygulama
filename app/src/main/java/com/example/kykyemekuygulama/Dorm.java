package com.example.kykyemekuygulama;

public class Dorm implements Comparable<Dorm> {
    private int dormID;
    private City city;
    private String dormName;

    public Dorm(int dormID, City city, String dormName) {
        this.dormID= dormID;
        this.city = city;
        this.dormName= dormName;
    }

    public int getStateID() {
        return dormID;
    }

    public City getCity() {
        return city;
    }

    public String getDormName() {
        return dormName;
    }

    @Override
    public String toString() {
        return dormName;
    }


    @Override
    public int compareTo(Dorm another) {
        return this.dormID - another.dormID;//ascending order
//            return another.getStateID()-this.getStateID();//descending order
    }
}
