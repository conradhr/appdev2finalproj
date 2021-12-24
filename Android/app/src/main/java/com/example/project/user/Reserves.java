package com.example.project.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import com.example.project.MainActivity;
import com.example.project.R;
import com.example.project.helpers.HttpsTrustManager;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.ArrayList;

public class Reserves extends AppCompatActivity  {
    RecyclerView recyclerView;
    ArrayList<String> list;
    TextView welcomeString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserves);
        recyclerView = findViewById(R.id.recyclerV);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] field = new String[1];
                field[0] = "username";
                //Creating array for data
                String[] data = new String[1];
                data[0] = MainActivity.loggedinuser;
                HttpsTrustManager.allowAllSSL();
                PutData putData = new PutData(MainActivity.LOCALHOST_URL + "/LoginRegister/getUserRes.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String[] cars = putData.getData().split("<br>");
                        Log.e("Cars: ", "" + cars[0]);
                    }
                }
            }
        });
    }
}