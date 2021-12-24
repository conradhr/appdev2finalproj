package com.example.project.POJOs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.MainActivity;
import com.example.project.R;
import com.example.project.authentication.login;
import com.example.project.helpers.HttpsTrustManager;
import com.example.project.helpers.ImageHelper;
import com.example.project.helpers.MapActivity;
import com.example.project.user.UserHome;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Car {
    private String car_id;
    private String car_brand;
    private String car_model;
    private String car_color;
    private String car_year;
    private String type;
    private String description;
    private String filename;
    private boolean availability;

    public Car() {
    }

    public Car(String car_id, String car_brand, String car_model, String car_color, String car_year, String type, String description, String filename, String availability) {
        this.car_id = car_id;
        this.car_brand = car_brand;
        this.car_model = car_model;
        this.car_color = car_color;
        this.car_year = car_year;
        this.type = type;
        this.description = description;
        this.filename = filename;
        this.availability = Boolean.parseBoolean(availability);
    }

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }

    public String getCar_brand() {
        return car_brand;
    }

    public void setCar_brand(String car_brand) {
        this.car_brand = car_brand;
    }

    public String getCar_model() {
        return car_model;
    }

    public void setCar_model(String car_model) {
        this.car_model = car_model;
    }

    public String getCar_color() {
        return car_color;
    }

    public void setCar_color(String car_color) {
        this.car_color = car_color;
    }

    public String getCar_year() {
        return car_year;
    }

    public void setCar_year(String car_year) {
        this.car_year = car_year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "Car{" +
                "car_id='" + car_id + '\'' +
                ", car_brand='" + car_brand + '\'' +
                ", car_model='" + car_model + '\'' +
                ", car_color='" + car_color + '\'' +
                ", car_year='" + car_year + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", filename='" + filename + '\'' +
                ", availability='" + availability + '\'' +
                '}';
    }

}
