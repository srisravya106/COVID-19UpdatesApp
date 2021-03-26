package com.example.covid_19updates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.Toast;

import com.example.covid_19updates.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        EndpointInterface anInterface=CoViD19instance.getRetrofitInstance().create(EndpointInterface.class);
        Call <String> c = anInterface.getData();
        c.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //Toast.makeText(MainActivity.this,""+response.body(),Toast.LENGTH_SHORT).show();
                try {
                    JSONArray rootArray = new JSONArray(response.body());
                    for(int i = 0;i < rootArray.length();i++) {
                        JSONObject rootObj = rootArray.getJSONObject(i);
                        String countryResult = rootObj.getString("Country");
                        String ConfirmedResult = rootObj.getString("Confirmed");
                        String DeathResult = rootObj.getString("Deaths");
                        String RecoveredResult = rootObj.getString("Recovered");
                        String ActiveResult = rootObj.getString("Active");
                        String DateResult = rootObj.getString("Date");

                        binding.activeTv.setText("Active " + ActiveResult);
                        binding.confirmedTv.setText("Confirmed " + ConfirmedResult);
                        binding.countryTv.setText("Country " + countryResult);
                        binding.dateTv.setText("Date " + DateResult);
                        binding.deathsTv.setText("Deaths " + DeathResult);
                        binding.recoveredTv.setText("Recovered " + RecoveredResult);
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this,"something went wrong with fetching data...",Toast.LENGTH_SHORT).show();
            }

            /*private String properDateFormat(String resultDate) {
                String inputFormat = "ddmmyy";
                String outputFormat = "yymmdd";
                SimpleDateFormat inputForm = new SimpleDateFormat(inputFormat);
                SimpleDateFormat outputForm = new SimpleDateFormat(outputFormat);
                Date d = null;
                String st = null;
                try {
                    d = inputForm.parse(resultDate);
                    st = outputForm.format(d);
                }
                catch(ParseException e) {
                    e.printStackTrace();
                }
                return st;
            }*/
        });

    }
}