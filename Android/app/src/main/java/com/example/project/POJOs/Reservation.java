package com.example.project.POJOs;

import java.io.Serializable;

public class Reservation {
    public String reservation_id;
    public String username;
    public String car_id;
    public String pickup_location;
    public String dropoff_location;
    public String pickup_date;
    public String dropoff_date;
    public String total_price;

    public Reservation() {
    }

    public Reservation(String reservation_id, String username, String car_id, String pickup_location, String dropoff_location, String pickup_date, String dropoff_date, String total_price) {
        this.reservation_id = reservation_id;
        this.username = username;
        this.car_id = car_id;
        this.pickup_location = pickup_location;
        this.dropoff_location = dropoff_location;
        this.pickup_date = pickup_date;
        this.dropoff_date = dropoff_date;
        this.total_price = total_price;
    }

    public String getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(String reservation_id) {
        this.reservation_id = reservation_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }

    public String getPickup_location() {
        return pickup_location;
    }

    public void setPickup_location(String pickup_location) {
        this.pickup_location = pickup_location;
    }

    public String getDropoff_location() {
        return dropoff_location;
    }

    public void setDropoff_location(String dropoff_location) {
        this.dropoff_location = dropoff_location;
    }

    public String getPickup_date() {
        return pickup_date;
    }

    public void setPickup_date(String pickup_date) {
        this.pickup_date = pickup_date;
    }

    public String getDropoff_date() {
        return dropoff_date;
    }

    public void setDropoff_date(String dropoff_date) {
        this.dropoff_date = dropoff_date;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservation_id='" + reservation_id + '\'' +
                ", username='" + username + '\'' +
                ", car_id='" + car_id + '\'' +
                ", pickup_location='" + pickup_location + '\'' +
                ", dropoff_location='" + dropoff_location + '\'' +
                ", pickup_date='" + pickup_date + '\'' +
                ", dropoff_date='" + dropoff_date + '\'' +
                ", total_price='" + total_price + '\'' +
                '}';
    }
}
