package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.project.POJOs.CarType;


import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    Button login, register;
    public static String loggedinuser;
    public final static String LOCALHOST_URL = "http://10.1.3.41/";
    public static ArrayList<CarType> carTypes = new ArrayList(Arrays.asList(new CarType("Electric","Great Vehicles","electric"), new CarType("Gas","Also Great","gas"),new CarType("Hybrid","Best of Both Worlds","hybrid")));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.button);
        register = findViewById(R.id.button2);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, com.example.project.authentication.register.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, com.example.project.authentication.login.class);
                startActivity(i);
            }
        });
    }
}