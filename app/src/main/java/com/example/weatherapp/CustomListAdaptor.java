package com.example.weatherapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdaptor extends ArrayAdapter<String>  {


    private final Activity context;
    private final String[] date;
    private final String[] max_temp;
    private final String[] min_temp;
    private final Integer[] icon_val;
    private final String[] description;


    public CustomListAdaptor(Activity context, String[] date,String[] max_temper,String[] min_temper,Integer[] icon,String[] description) {
        super(context, R.layout.datelist, date);
        this.context = context;
        this.date = date;
        this.max_temp = max_temper;
        this.min_temp = min_temper;
        this.icon_val = icon;
        this.description = description;
    }

    public View getView(int position,View view,ViewGroup parent) {
        if(max_temp.length>0){
            LayoutInflater inflater=context.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.datelist, null,true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.date);
            ImageView imageView = (ImageView)rowView.findViewById(R.id.image_view);
            TextView txtDes = (TextView)rowView.findViewById(R.id.maind);
            TextView txt_matemp = (TextView) rowView.findViewById(R.id.ma_temp);
            TextView txt_mitemp = (TextView) rowView.findViewById(R.id.mi_temp);
            txtTitle.setText(date[position]);
            txtDes.setText(description[position]);
            txt_matemp.setText(max_temp[position] + "°C");
            txt_mitemp.setText(min_temp[position] + "°C");
            imageView.setImageResource(icon_val[position]);
            return rowView;
        }
        else{
            return null;
        }
    }
}