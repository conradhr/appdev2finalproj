package com.example.project.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project.MainActivity;
import com.example.project.R;
import com.example.project.helpers.HttpsTrustManager;
import com.example.project.user.UserHome;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.Arrays;

public class register extends AppCompatActivity {

    Button cont;
    EditText first, last, email, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        cont = findViewById(R.id.button3);
        first = findViewById(R.id.fname);
        last = findViewById(R.id.lname);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.passsword);

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[4];
                        field[0] = "fullname";
                        field[1] = "username";
                        field[2] = "password";
                        field[3] = "email";
                        Log.i("field: ", Arrays.toString(field));
                        //Creating array for data
                        String[] data = new String[4];
                        data[0] = first.getText().toString() + " " + last.getText().toString();
                        data[1] = email.getText().toString().split("@")[0];
                        data[2] = pass.getText().toString();
                        data[3] = email.getText().toString();
                        Log.i("data: ", Arrays.toString(data));
                        HttpsTrustManager.allowAllSSL();
                        PutData putData = new PutData(MainActivity.LOCALHOST_URL + "/LoginRegister/signup.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                if (result.equals("Sign Up Success")){
                                    MainActivity.loggedinuser = email.getText().toString().split("@")[0];
                                    Intent intent = new Intent(register.this, UserHome.class);
                                    startActivity(intent);
                                    finish();
                                } else {Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();}
                            }
                        }
                    }
                });
            }
        });
    }
}