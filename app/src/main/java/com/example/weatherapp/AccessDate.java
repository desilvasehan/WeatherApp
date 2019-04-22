package com.example.weatherapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class AccessDate extends AppCompatActivity {
    int d_no = 0;
    String color = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_date);
        startup();
    }
    public void startup(){
        String stream_data = getIntent().getStringExtra("Catch_Date");
        List<SliceValue> pieData = new ArrayList<>();
        colorset();
        pieData.add(new SliceValue(MainActivity.Humidity[d_no], Color.parseColor(color)));
        pieData.add(new SliceValue((100 - MainActivity.Humidity[d_no]), Color.parseColor("#0000ffff")));
        for(int i=0;i<5;i++){
            if(stream_data.equals(MainActivity.dates_string[i])){
                d_no = i;
                break;
            }else {
                continue;
            }
        }

        TextView txt_1 = findViewById(R.id.txt_date);
        TextView txt_2 = findViewById(R.id.min_temp);
        TextView txt_3 = findViewById(R.id.max_temp);
        ImageView img_1 = findViewById(R.id.image_large);
        TextView txt_4 = findViewById(R.id.pressurebar);
        PieChartView pieChartView = findViewById(R.id.piechart);
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasCenterCircle(true).setCenterText1(MainActivity.Humidity[d_no] + "%").setCenterText1FontSize(30).setCenterText1Color(Color.parseColor("#FFFFFF"));
        pieChartView.setPieChartData(pieChartData);
        txt_1.setText(MainActivity.dates_string[d_no]);
        txt_2.setText(MainActivity.min_temp[d_no] + "°C");
        txt_3.setText(MainActivity.max_temp[d_no] + "°C");
        txt_4.setText(MainActivity.Pressure[d_no]);
        new CustomListAdaptor.DownloadImageFromInternet(img_1).execute(MainActivity.icon[d_no]);
    }

    public void colorset(){
        String pressure = MainActivity.Pressure[d_no];
        Float F_pressure = Float.parseFloat(pressure);
        if (F_pressure>80.0){
            color = "#6200EA";
        }
        else if(F_pressure>75){
            color = "#651FFF";
        }
        else if (F_pressure>50){
            color = "#7C4DFF";
        }
        else{
            color = "#B388FF";
        }
    }
}
