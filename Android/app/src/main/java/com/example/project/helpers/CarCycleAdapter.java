package com.example.project.helpers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.MainActivity;
import com.example.project.POJOs.Car;
import com.example.project.R;
import com.example.project.authentication.login;
import com.example.project.user.UserHome;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.List;

public class CarCycleAdapter extends RecyclerView.Adapter <CarCycleAdapter.ViewHolder> {

    private Context context;
    private List<Car> myData;
    private LayoutInflater myInflater; //class that converts XML to java object
    ClickListener ClickListener;

    public CarCycleAdapter(Context context, List<Car> data, ClickListener ClickListener){
        this.context = context;
        this.myData = data;
        this.ClickListener = ClickListener;
    }

    public interface ClickListener {
        Car getItem(int position);
        void clickItem(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(com.example.project.R.layout.car_child, parent, false);
        return new ViewHolder(view, ClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Car car = myData.get(position);
        holder.title.setText(car.getCar_brand() + " " + car.getCar_model());
        holder.desc.setText(car.getDescription());
        holder.year.setText(car.getCar_year());
        holder.color.setText(car.getCar_color());
        holder.availability.setText(car.getAvailability() ? "Available" : "Not Available");
        holder.availability.setTextColor(Color.parseColor(car.getAvailability() ? "#00b300" : "#b30000"));
        new ImageHelper(context).setImageView(holder.picture, car.getFilename());
    }

    @Override
    public int getItemCount() {
        return myData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView picture, favButton;
        ClickListener ClickListener;
        TextView title, color, year, availability, desc;

        public ViewHolder(@NonNull View itemView, ClickListener ClickListener) {
            super(itemView);
            this.ClickListener = ClickListener;
            title = itemView.findViewById(R.id.CarCycleTitle);
            desc = itemView.findViewById(R.id.CarCycleDesc);
            color = itemView.findViewById(R.id.CarCycleColor);
            year = itemView.findViewById(R.id.CarCycleYear);
            availability = itemView.findViewById(R.id.CarCycleAvailability);
            picture = itemView.findViewById(R.id.CarCycleImg);
            favButton = itemView.findViewById(R.id.favButton);
            favButton.setImageResource(R.drawable.heartempty);
            favButton.setTag("empty");

            favButton.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view == favButton && favButton.getTag().toString().equals("empty")) {
                Log.e("Fave car: ", "" + ClickListener.getItem(getAdapterPosition()).getCar_model());
                favButton.setImageResource(R.drawable.heartfilled);
                favButton.setTag("filled");
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[2];
                        field[0] = "username";
                        field[1] = "car_id";
                        //Creating array for data
                        String[] data = new String[2];
                        data[0] = MainActivity.loggedinuser;
                        data[1] = ClickListener.getItem(getAdapterPosition()).getCar_id();
                        HttpsTrustManager.allowAllSSL();
                        PutData putData = new PutData(MainActivity.LOCALHOST_URL + "/LoginRegister/putUserFaves.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                    Log.e("result: ", result);
                            }
                        }
                    }
                });
            }

            else if (view == favButton && favButton.getTag().toString().equals("filled")) {
                Log.e("unfave car: ", "" + ClickListener.getItem(getAdapterPosition()).getCar_model());
                favButton.setImageResource(R.drawable.heartempty);
                favButton.setTag("empty");
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[2];
                        field[0] = "username";
                        field[1] = "car_id";
                        //Creating array for data
                        String[] data = new String[2];
                        data[0] = MainActivity.loggedinuser;
                        data[1] = ClickListener.getItem(getAdapterPosition()).getCar_id();
                        HttpsTrustManager.allowAllSSL();
                        PutData putData = new PutData(MainActivity.LOCALHOST_URL + "/LoginRegister/deleteUserFaves.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                    Log.e("result: ", result);
                            }
                        }
                    }
                });
            } else ClickListener.clickItem(getAdapterPosition());
        }
    }
}