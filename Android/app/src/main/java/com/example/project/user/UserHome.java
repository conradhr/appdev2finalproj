package com.example.project.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.MainActivity;
import com.example.project.POJOs.Car;
import com.example.project.POJOs.User;
import com.example.project.R;
import com.example.project.helpers.MainCycleAdapter;
import com.vishnusivadas.advanced_httpurlconnection.FetchData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class UserHome extends AppCompatActivity implements MainCycleAdapter.ClickListener {

    TextView welcomeString;
    ImageButton faves, reserves;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userhome);
        welcomeString = findViewById(R.id.welcomeString);
        faves = findViewById(R.id.favoritesButton);

        welcomeString.setText("Welcome " + MainActivity.loggedinuser);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                /*FetchData fetchData = new FetchData(MainActivity.LOCALHOST_URL + "/LoginRegister/getUsers.php");
                if (fetchData.startFetch()) {
                    if (fetchData.onComplete()) {
                        users = fetchData.getData().split("<br>");
                        Log.e("FetchData users: ", "" + users[0]);
                        Log.e("FetchData users: ", "" + users[1]);
                        for (String user: users) {
                            String[] userArray = user.split(",");
                            Log.e("userArray: ", "" + Arrays.toString(userArray) + " LENGTH= " + userArray.length);
                            User newUser = new User(userArray[0],userArray[1],userArray[2],userArray[3],userArray[4],userArray[5]);
                            usersList.add(newUser);
                        }
                    }
                }*/

                //set up the recyclerview
                RecyclerView recyclerView = findViewById(R.id.MainRecycler);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                //recyclerView.setAdapter(new MainCycleAdapter(usersList, UserHome.this));
                recyclerView.setAdapter(new MainCycleAdapter(getApplicationContext(), MainActivity.carTypes,UserHome.this));
            }
        });

        faves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserHome.this, Faves.class);
                startActivity(i);
            }
        });

        reserves = findViewById(R.id.reservationsButton);
        reserves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserHome.this, Reserves.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void getItem(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserHome.this)
                .setTitle(MainActivity.carTypes.get(position).getTitle())
                .setMessage("Are you sure, you want to continue ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Class<?> c = null;
                        try {
                            c = Class.forName("com.example.project.user." + MainActivity.carTypes.get(position).getTitle().toLowerCase());
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(getApplicationContext(), c);
                        startActivity(intent);
                        Toast.makeText(UserHome.this,"Selected Option: " + MainActivity.carTypes.get(position).getTitle(),Toast.LENGTH_SHORT).show();
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
        dialog.show();
    }
}
        /* Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_fav, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        //recyclerView.setAdapter(new RecyclerViewAdapter(view.getContext(), ));

        return view; CODE FOR FRAG RECYCLE VIEW


        AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle(usersList.get(position).getFullname())
                .setMessage(usersList.get(position).getPassword())
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        dialog.show();

        */