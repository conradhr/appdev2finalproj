package com.example.project.helpers;

import android.content.Context;
import android.widget.ImageView;

public class ImageHelper {
    Context context;

    public ImageHelper(Context context) {
        this.context = context;
    }

    public ImageView setImageView(ImageView imageView, String fileName) {
        if (fileName.startsWith("http")){
            new DownloadImageTask(imageView).execute(fileName);
        } else if (fileName.isEmpty() || fileName.equals("null")) {
            return imageView;
        } else {
                final int resourceId = context.getResources().getIdentifier(fileName, "drawable", context.getPackageName());
                imageView.setImageResource(resourceId);
        }
        return imageView;
    }
}
