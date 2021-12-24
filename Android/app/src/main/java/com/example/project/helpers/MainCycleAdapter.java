package com.example.project.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.POJOs.CarType;
import com.example.project.R;

import java.util.List;

public class MainCycleAdapter extends RecyclerView.Adapter <MainCycleAdapter.ViewHolder> {
    private Context context;
    private List<CarType> myData;
    private LayoutInflater myInflater; //class that converts XML to java object
    ClickListener ClickListener;

    public MainCycleAdapter(Context context, List<CarType> data, ClickListener ClickListener){
        this.context = context;
        this.myInflater = LayoutInflater.from(context);
        this.myData = data;
        this.ClickListener = ClickListener;
    }

    public interface ClickListener {
        void getItem(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_child, parent, false);
        return new ViewHolder(view, ClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CarType carType = myData.get(position);
        holder.title.setText(carType.getTitle());
        holder.desc.setText(carType.getDesc());

        new ImageHelper(context).setImageView(holder.picture, carType.getImg());

        /*if (carType.getImg().startsWith("http")){
            new DownloadImageTask(holder.picture).execute(carType.getImg());
        } else {
            final int resourceId = context.getResources().getIdentifier(carType.getImg(), "drawable", context.getPackageName());
            holder.picture.setImageResource(resourceId);
        }*/
    }

    @Override
    public int getItemCount() {
        return myData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView picture;
        ClickListener ClickListener;
        TextView title, desc;

        public ViewHolder(@NonNull View itemView, ClickListener ClickListener) {
            super(itemView);
            title = itemView.findViewById(R.id.CarCycleTitle);
            desc = itemView.findViewById(R.id.CarCycleDesc);
            picture = itemView.findViewById(R.id.CarCycleImg);
            this.ClickListener = ClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ClickListener.getItem(getAdapterPosition());
        }
    }
}
