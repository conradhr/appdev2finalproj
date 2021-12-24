package com.example.project.helpers;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.project.MainActivity;
import com.example.project.POJOs.Car;
import com.example.project.POJOs.Reservation;
import com.example.project.authentication.login;
import com.example.project.user.UserHome;
import com.example.project.user.gas;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class LocationHelper {
    Context context;
    Geocoder coder;

    public LocationHelper(Context context) {
        this.context = context;
    }

    public LatLng getLocationFromAddress(String strAddress) {

        coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    public String getAddressFromLocation(LatLng latLng) {

        coder = new Geocoder(context);
        List<Address> addresses = null;

        try {
            addresses = coder.getFromLocation(latLng.getLatitude(), latLng.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addresses.get(0).getAddressLine(0);
    }



    static class ReservationHelper {
        Reservation reservation;

        public ReservationHelper(Reservation reservation) {
            this.reservation = reservation;
        }

        public double getReservationPrice(){
            double price = 0;
            price = 13.99 + (0.05 * distance()) + (0.05 * timeDifference());
            return price;
        }

        public double distance(){
            String[] pickup = reservation.getPickup_location().split("/");
            String[] dropoff = reservation.getDropoff_location().split("/");

            LatLng latLng = new LatLng(Double.parseDouble(pickup[0]), Double.parseDouble(pickup[1]));
            LatLng latLng2 = new LatLng(Double.parseDouble(dropoff[0]), Double.parseDouble(dropoff[1]));

            return latLng.distanceTo(latLng2);
        }

        public long timeDifference() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/mm/dd", Locale.ENGLISH);
            Date firstDate = null;
            Date secondDate = null;
            try {
                firstDate = sdf.parse(reservation.getPickup_date());
                secondDate = sdf.parse(reservation.getDropoff_date());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            long milliseconds = Math.abs(secondDate.getTime() - firstDate.getTime());
            long diff = TimeUnit.DAYS.convert(milliseconds, TimeUnit.MILLISECONDS);
            return diff;
        }

    }
}