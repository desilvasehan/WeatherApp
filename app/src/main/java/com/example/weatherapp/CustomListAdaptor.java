package com.example.weatherapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;


public class CustomListAdaptor extends ArrayAdapter<String>  {


    private final Activity context;
    private final String[] date;
    private final String[] max_temp;
    private final String[] min_temp;
    private final String[] icon_val;

    public CustomListAdaptor(Activity context, String[] date,String[] max_temper,String[] min_temper,String[] icon) {
        super(context, R.layout.datelist, date);
        this.context = context;
        this.date = date;
        this.max_temp = max_temper;
        this.min_temp = min_temper;
        this.icon_val = icon;
    }

    public static class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap>{
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }

    public View getView(int position,View view,ViewGroup parent) {
        if(max_temp.length>0){
            LayoutInflater inflater=context.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.datelist, null,true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.date);
            ImageView imageView = (ImageView)rowView.findViewById(R.id.image_view);
            TextView txt_matemp = (TextView) rowView.findViewById(R.id.ma_temp);
            TextView txt_mitemp = (TextView) rowView.findViewById(R.id.mi_temp);
            txtTitle.setText(date[position]);
            txt_matemp.setText("Max : " + max_temp[position] + "°C");
            txt_mitemp.setText("Min : " + min_temp[position] + "°C");
            new DownloadImageFromInternet(imageView).execute(icon_val[position]);

            return rowView;
        }
        else{
            return null;
        }
    }

}