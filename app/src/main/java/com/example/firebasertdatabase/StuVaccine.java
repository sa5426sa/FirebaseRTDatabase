package com.example.firebasertdatabase;

import androidx.annotation.NonNull;

public class StuVaccine {
    private String Place, Date;

    public StuVaccine(String place, String date) {
        Place = place;
        Date = date;
    }

    public StuVaccine() {}

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    @NonNull
    @Override
    public String toString() {
        return "StuVaccine{" +
                "Place='" + Place + '\'' +
                ", Date='" + Date + '\'' +
                '}';
    }
}
