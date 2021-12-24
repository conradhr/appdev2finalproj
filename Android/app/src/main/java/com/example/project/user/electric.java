package com.example.project.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import com.example.project.MainActivity;
import com.example.project.POJOs.Car;
import com.example.project.POJOs.Reservation;
import com.example.project.R;
import com.example.project.helpers.CarCycleAdapter;
import com.example.project.helpers.HttpsTrustManager;
import com.example.project.helpers.ImageHelper;
import com.example.project.helpers.LocationHelper;
import com.example.project.helpers.MapActivity;
import com.google.gson.Gson;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import com.vishnusivadas.advanced_httpurlconnection.PutData;


import java.util.ArrayList;
import java.util.Arrays;

public class electric extends AppCompatActivity implements CarCycleAdapter.ClickListener {

    RecyclerView recyclerView;
    FrameLayout gasFrame;
    TextView welcomeString;
    ArrayList<Car> carsList = new ArrayList<>();
    Dialog dialog;
    ImageButton faves, reserves;
    Bundle savedInstanceState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas);
        Mapbox.getInstance(this,getString(R.string.access_token));
        recyclerView = findViewById(R.id.gasRecycler);
        faves = findViewById(R.id.favoritesButton);
        gasFrame = findViewById(R.id.GasContainer);
        this.savedInstanceState = savedInstanceState;
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] field = new String[1];
                field[0] = "type";
                //Creating array for data
                String[] data = new String[1];
                data[0] = "electric";
                HttpsTrustManager.allowAllSSL();
                PutData putData = new PutData(MainActivity.LOCALHOST_URL + "/LoginRegister/getCarType.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String[] cars = putData.getData().split("<br>");
                        Log.e("Cars: ", "" + cars[0]);
                        for (String car: cars) {
                            String[] carArray = car.split(",");
                            Log.e("carArray: ", "" + Arrays.toString(carArray) + " LENGTH= " + carArray.length);
                            Car newCar = new Car(carArray[0],carArray[1],carArray[2],carArray[3],carArray[4],carArray[5],carArray[6],carArray[7],carArray[8]);
                            carsList.add(newCar);
                        }
                    }
                }

                //set up the recyclerview
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(new CarCycleAdapter(getApplicationContext(),carsList, electric.this));
            }
        });

        faves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(electric.this, Faves.class);
                startActivity(i);
            }
        });

        reserves = findViewById(R.id.reservationsButton);
        reserves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(electric.this, Reserves.class);
                startActivity(i);
            }
        });
    }

    @Override
    public Car getItem(int position) {
        return carsList.get(position);
    }

    @Override
    public void clickItem(int position) {

        Reservation newReservation = new Reservation();
        newReservation.setCar_id(carsList.get(position).getCar_id());
        newReservation.setUsername(MainActivity.loggedinuser);

        dialog = new Dialog(electric.this);
        dialog.setContentView(R.layout.reservationdialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView text1, text2, text3, text4;
        ImageView dialogImg = dialog.findViewById(R.id.dialogStartDate);
        new ImageHelper(this).setImageView(dialogImg, carsList.get(position).getFilename());

        text1 = dialog.findViewById(R.id.dialogText);
        text1.setText(carsList.get(position).getCar_brand()  + " " + carsList.get(position).getCar_model());
        text2 = dialog.findViewById(R.id.dialogText2);
        text2.setText("Year: " + carsList.get(position).getCar_year());
        text3 = dialog.findViewById(R.id.dialogText3);
        text3.setText("Color: " + carsList.get(position).getCar_color());
        text4 = dialog.findViewById(R.id.dialogText4);
        text4.setText(carsList.get(position).getDescription());

        ImageView imageViewClose = dialog.findViewById(R.id.dialogClose);
        Button continueBtn = dialog.findViewById(R.id.dialogContinueBtn);

        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog.setContentView(R.layout.datedialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                DatePicker datePicker;
                TimePicker timePicker;
                ImageView imageViewClose = dialog.findViewById(R.id.dialogClose);
                Button continueBtn = dialog.findViewById(R.id.dialogContinueBtn);

                datePicker = dialog.findViewById(R.id.dialogStartDate);
                timePicker = dialog.findViewById(R.id.dialogStartTime);

                imageViewClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                continueBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        newReservation.setPickup_date(datePicker.getYear() + "/" + (datePicker.getMonth()+1)  + "/" + datePicker.getDayOfMonth() + " " + timePicker.getHour() + ":" + timePicker.getMinute());
                        dialog.setContentView(R.layout.enddatedialog);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        DatePicker datePicker;
                        TimePicker timePicker;
                        ImageView imageViewClose = dialog.findViewById(R.id.dialogClose);
                        Button continueBtn = dialog.findViewById(R.id.dialogContinueBtn);

                        datePicker = dialog.findViewById(R.id.dialogEndDate);
                        timePicker = dialog.findViewById(R.id.dialogEndTime);

                        imageViewClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        continueBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                newReservation.setDropoff_date(datePicker.getYear() + "/" + (datePicker.getMonth()+1)  + "/" + datePicker.getDayOfMonth() + " " + timePicker.getHour() + ":" + timePicker.getMinute());
                                Intent intent = new Intent(electric.this, MapActivity.class);
                                Gson gson = new Gson();
                                String myJson = gson.toJson(newReservation);
                                intent.putExtra("myreservation", myJson);
                                startActivity(intent);
                            }
                        });
                        dialog.show();
                    }
                });
                dialog.show();
            }
        });
        dialog.show();

        /*
        AlertDialog.Builder builder = new AlertDialog.Builder(gas.this)
                .setTitle(carsList.get(position).getCar_brand() + " " + carsList.get(position).getCar_model())
                .setMessage("Are you sure, you want to reserve this vehicle?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(gas.this,"Selected Option: YES", Toast.LENGTH_LONG).show();
                        recyclerView.setVisibility(RecyclerView.GONE);
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.add(R.id.GasContainer, new reservationdetails());
                        ft.addToBackStack("reservationdetails");
                        ft.commit();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing
                    }
                });
        //Creating dialog box
        AlertDialog dialog  = builder.create();
        dialog.show();*/
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();
            recyclerView.setVisibility(recyclerView.VISIBLE);
        } else
            super.onBackPressed();
    }
}