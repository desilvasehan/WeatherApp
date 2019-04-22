package com.example.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


public class AccessDate extends AppCompatActivity {
    int d_no = 0;
    String color = "#3F51B5";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_date);
        startup();
    }
    public void startup(){
        String stream_data = getIntent().getStringExtra("Catch_Date");

        for(int i=0;i<5;i++){
            if(stream_data.equals(MainActivity.dates_string[i])){
                d_no = i;
                break;
            }else {
                continue;
            }
        }

        TextView txt_1 = findViewById(R.id.txt_date);
        TextView txt_2 = findViewById(R.id.avg_temp);
        ImageView img_1 = findViewById(R.id.image_large);

        float avg_temp = (Float.valueOf(MainActivity.min_temp[d_no]) + Float.valueOf(MainActivity.min_temp[d_no]))/2;
        txt_1.setText(MainActivity.dates_string[d_no]);
        txt_2.setText(avg_temp + "°C");
        img_1.setImageResource(MainActivity.icon[d_no]);
    }

}
