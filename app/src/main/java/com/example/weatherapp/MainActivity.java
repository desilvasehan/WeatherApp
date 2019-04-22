package com.example.weatherapp;

import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {
    public static String[] dates_string,min_temp,max_temp, main,description,Pressure;
    public static Integer[] Humidity;
    public static final int RequestPermissionsCode = 2;
    public Integer[] icon_list = {
            R.drawable.clear,
            R.drawable.clouds,
            R.drawable.drizzel,
            R.drawable.mist,
            R.drawable.rain,
            R.drawable.snow,
            R.drawable.thunderstorm
    };

    public static Integer[] icon;

    String[] permissions = {
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    ListView list;

    String test = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EnableRuntimePermission();
        //onRequestPermissionsResult(RequestPermissionsCode,permissions,);

        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
            FetchData data = new FetchData();
            data.execute("d79c0d8b5c0cfc7f8e8a7a2ca236fa70");
        }

        list = (ListView) findViewById(R.id.list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String click_value =String.valueOf(parent.getItemAtPosition(position));
                Intent pass_intent = new Intent(view.getContext(),AccessDate.class);
                pass_intent.putExtra("Catch_Date",click_value);
                startActivityForResult(pass_intent,0);
            }
        });
    }


    public void EnableRuntimePermission(){

        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.INTERNET)){
            Toast.makeText(MainActivity.this,"Allow Internet Permission",Toast.LENGTH_LONG).show();
        } else{
            ActivityCompat.requestPermissions(MainActivity.this,permissions, RequestPermissionsCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[],int[] grantResults){
        switch(requestCode){
            case RequestPermissionsCode:
                if (grantResults.length>0 && (grantResults[0]== PackageManager.PERMISSION_GRANTED) ){
                    Toast.makeText(MainActivity.this,"Internet Permission Granted",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this,"Internet Permission Cancelled",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public class FetchData extends AsyncTask<String, Void, String> {
        HttpURLConnection urlConnection = null;
        BufferedReader bufferReader = null;
        String forecastJsonStr = null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            test  = forecastJsonStr;
            readWeather(test);
            display();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                final String BASE_URL ="http://api.openweathermap.org/data/2.5/forecast/daily?q=Colombo&mode=json&units=metric&cnt=6";
                final String KEY = "APPID";
                Uri uriBuild1 = Uri.parse(BASE_URL).buildUpon().appendQueryParameter(KEY,params[0]).build();
                URL url1= new URL(uriBuild1.toString());
                urlConnection = (HttpURLConnection) url1.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer stringBuffer = new StringBuffer();
                String line1;
                if (inputStream == null ) {
                    return null;
                }
                bufferReader = new BufferedReader(new InputStreamReader(inputStream));
                while ((line1 = bufferReader.readLine()) != null) {
                    stringBuffer.append(line1 + "\n");
                }
                if (stringBuffer.length() == 0) {
                    return null;
                }
                forecastJsonStr = stringBuffer.toString();
            } catch (Exception e){
                Log.e("Sehan", "Error closing stream", e);
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (bufferReader != null) {
                    try {
                        bufferReader.close();
                    } catch (final IOException e) {
                        Log.e("Sehan", "Error closing stream", e);
                    }
                }
            }
            return null;
        }
    }

    public void readWeather(String str_test){
        try {
            JSONObject rootObject = new JSONObject(str_test);
            JSONArray list = rootObject.getJSONArray("list");
            String[] temp = new String[list.length()];
            String temp2;
            max_temp = new String[6];
            min_temp = new String[6];
            main = new String[6];
            Pressure = new String[6];
            Humidity = new Integer[6];
            description = new String[6];
            icon = new Integer[6];

            for (int i=0;i<list.length();i++){
                JSONObject a = list.getJSONObject(i);
                temp[i] = a.getString("temp");
                Pressure[i] = a.getString("pressure");
                temp2 = a.getString("humidity");
                Humidity[i] = Integer.parseInt(temp2);
                temp2 = a.getString("weather");

                JSONObject subroot1 = new JSONObject(temp[i]);
                max_temp[i] = subroot1.getString("min");
                min_temp[i] = subroot1.getString("max");

                temp2 = temp2.replace('[',' ');
                temp2 = temp2.replace(']',' ');

                JSONObject subroot2 = new JSONObject(temp2);
                main[i] = subroot2.getString("main");
                description[i] = subroot2.getString("description");
            }

            for(int j=0;j<6;j++){
                if(main[j].equals("Clear")){
                    icon[j] = (icon_list[0]);
                }
                else if (main[j].equals("Clouds")){
                    icon[j] = (icon_list[1]);
                }
                else if (main[j].equals("Drizzle")){
                    icon[j] = (icon_list[3]);
                }
                else if (main[j].equals("Rain")){
                    icon[j] = (icon_list[4]);
                }
                else if (main[j].equals("Snow")){
                    icon[j] = (icon_list[5]);
                }
                else if (main[j].equals("Thunderstorm")){
                    icon[j] = (icon_list[6]);
                }
                else{
                    icon[j] = (icon_list[2]);
                }
                System.out.println("Main : " + main[j]);
                System.out.println("Icon name : " + icon[j]);
            }
        }
        catch (JSONException je){
            Log.d("json",je.toString());
        }
    }

    public void display(){

        try {
            LocalDate date = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            dates_string=new String[6];
            for(int i=0;i<6;i++){
                dates_string[i] = date.format(formatter);
                date = date.plusDays(1);
            }
            final CustomListAdaptor adaptor = new CustomListAdaptor(this,dates_string,max_temp,min_temp, icon);
            list = (ListView) findViewById(R.id.list);
            list.setAdapter((ListAdapter) adaptor);
            System.out.println("Done!");
        }catch (Exception e){
            System.out.println("Error Occured : " + e);
        }

    }


}
