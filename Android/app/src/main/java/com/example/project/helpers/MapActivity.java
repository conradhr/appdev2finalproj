package com.example.project.helpers;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.project.MainActivity;
import com.example.project.POJOs.Car;
import com.example.project.POJOs.Reservation;
import com.example.project.R;
import com.example.project.authentication.register;
import com.example.project.user.UserHome;
import com.example.project.user.gas;
import com.google.gson.Gson;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.Arrays;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    LatLng position;
    MapView mapView;
    MapboxMap map;
    EditText address;
    Button continueBtn, confirmBtn;
    Reservation reservation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this,getString(R.string.access_token));
        setContentView(R.layout.activity_map);
        Gson gson = new Gson();
        reservation = gson.fromJson(getIntent().getStringExtra("myreservation"), Reservation.class);
        address = findViewById(R.id.dialogSearchEditText);
        continueBtn = findViewById(R.id.dialogContinueBtn);
        confirmBtn = findViewById(R.id.dialogConfirmBtn);
        confirmBtn.setVisibility(View.GONE);
        mapView = (MapView) findViewById(R.id.mapboxmap);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    public void setCameraPosition(LatLng location){
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 13.0));
    }
    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        map = mapboxMap;
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    position = new LocationHelper(MapActivity.this).getLocationFromAddress(address.getText().toString());
                    setCameraPosition(position);
                    map.addMarker(new MarkerOptions().position(position));
                } catch (Exception e) {}
                continueBtn.setVisibility(View.GONE);
                confirmBtn.setVisibility(View.VISIBLE);
                confirmBtn.setText("CONFIRM PICKUP LOCATION");
                confirmBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reservation.setPickup_location(""+position.getLatitude()+"/"+position.getLongitude());
                        continueBtn.setVisibility(View.VISIBLE);
                        confirmBtn.setVisibility(View.GONE);
                        address.setHint("Enter dropoff address");
                        address.setText(null);
                        continueBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    position = new LocationHelper(MapActivity.this).getLocationFromAddress(address.getText().toString());
                                    setCameraPosition(position);
                                    map.addMarker(new MarkerOptions().position(position));
                                } catch (Exception e) {}
                                continueBtn.setVisibility(View.GONE);
                                confirmBtn.setVisibility(View.VISIBLE);
                                confirmBtn.setText("CONFIRM DROPOFF LOCATION");
                                confirmBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        reservation.setDropoff_location(""+position.getLatitude()+"/"+position.getLongitude());
                                        reservation.setTotal_price("" + new LocationHelper.ReservationHelper(reservation).getReservationPrice());
                                        Handler handler = new Handler(Looper.getMainLooper());
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                String[] field = new String[7];
                                                field[0] = "username";
                                                field[1] = "car_id";
                                                field[2] = "pickuplocation";
                                                field[3] = "dropofflocation";
                                                field[4] = "pickupdate";
                                                field[5] = "dropoffdate";
                                                field[6] = "price";
                                                //Creating array for data
                                                String[] data = new String[7];
                                                data[0] = reservation.getUsername();
                                                data[1] = reservation.getCar_id();
                                                data[2] = reservation.getPickup_location();
                                                data[3] = reservation.getDropoff_location();
                                                data[4] = reservation.getPickup_date();
                                                data[5] = reservation.getDropoff_date();
                                                data[6] = reservation.getTotal_price();
                                                HttpsTrustManager.allowAllSSL();
                                                PutData putData = new PutData(MainActivity.LOCALHOST_URL + "/LoginRegister/putUserRes.php", "POST", field, data);
                                                if (putData.startPut()) {
                                                    if (putData.onComplete()) {
                                                        String result = putData.getResult();
                                                        if (result.equals("Success")){
                                                            Intent intent = new Intent(MapActivity.this, UserHome.class);
                                                            startActivity(intent);
                                                            finish();
                                                        } else { Log.e("error: ", result); }
                                                    }
                                                }
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

}