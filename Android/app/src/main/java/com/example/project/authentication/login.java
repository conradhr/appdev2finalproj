package com.example.project.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project.MainActivity;
import com.example.project.R;
import com.example.project.helpers.HttpsTrustManager;
import com.example.project.user.UserHome;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class login extends AppCompatActivity {

    Button login;
    EditText user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.button4);
        user = findViewById(R.id.user);
        pass = findViewById(R.id.loginpassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = user.getText().toString();
                final String password = pass.getText().toString();

                //do some checks

                //passes all the checks
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[2];
                        field[0] = "username";
                        field[1] = "password";
                        //Creating array for data
                        String[] data = new String[2];
                        data[0] = username;
                        data[1] = password;
                        HttpsTrustManager.allowAllSSL();
                        PutData putData = new PutData(MainActivity.LOCALHOST_URL + "/LoginRegister/login.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                if (result.equals("Login Success")) {
                                    MainActivity.loggedinuser = username;
                                    Intent intent = new Intent(login.this, UserHome.class);
                                    startActivity(intent);
                                    finish();
                                } else {Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();}
                            }
                        }
                    }
                });
            };
        });
    }
}