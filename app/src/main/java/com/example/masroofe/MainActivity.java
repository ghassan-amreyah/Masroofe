package com.example.masroofe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private TextView FixedIncome, Cash,SumRev,SumExp;
    private ImageView imgMonthsRecord, imgUserGuide, imgSetting;
    private FloatingActionButton ParAddButton;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    public static final String LOGIN = "LOGIN";
    public static final String USERNAME = "USERNAME";
    public static final String FIXEDINCOME = "FIXEDINCOME";
    public static final String CASH = "CASH";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        checkUserLogin();
        setupReference();
        setupParActions();
    }

    private void checkUserLogin() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        String login = prefs.getString(LOGIN, "false");
        if (login.equalsIgnoreCase("false")){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void setupReference() {
        FixedIncome = findViewById(R.id.fixedIncome);
        SumRev = findViewById(R.id.sumRev);
        SumExp = findViewById(R.id.sumExp);
        Cash = findViewById(R.id.cash);
        FixedIncome.setText(prefs.getString(FIXEDINCOME,"0"));
        Cash.setText(prefs.getString(CASH,"0"));
        countExpensesAndRevenuesAndCash(prefs.getString(USERNAME,""));
    }

    private void countExpensesAndRevenuesAndCash(String username) {
        String url = "https://adam.s-matar.com/android-restAPI/countexpandrevandcash.php";
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getString("error").equalsIgnoreCase("true")){
                        Toast.makeText(MainActivity.this, "حدث خطأ!", Toast.LENGTH_SHORT).show();
                    }else{
                        if(!jsonObject.getString("expenses").equalsIgnoreCase("null")){
                            SumExp.setText(jsonObject.getString("expenses"));
                        }else {
                            SumExp.setText("0");
                        }
                        if(!jsonObject.getString("revenues").equalsIgnoreCase("null")){
                            SumRev.setText(jsonObject.getString("revenues"));
                        }else {
                            SumRev.setText("0");
                        }
                        Cash.setText(jsonObject.getString("cash"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String,String>();

                LocalDate now = LocalDate.now();
                int year = now.getYear();
                int month = now.getMonthValue();

                params.put("username", username);
                params.put("year", ""+year);
                if (month<10){
                    params.put("month", "0"+month);
                }else {
                    params.put("month", String.valueOf(month));
                }
                return params;
            }
        };
        queue.add(request);
    }

    private void setupParActions() {

        imgMonthsRecord = findViewById(R.id.imgMonthsRecord);
        imgUserGuide = findViewById(R.id.imgUserGuide);
        imgSetting = findViewById(R.id.imgSetting);
        ParAddButton = findViewById(R.id.ParAddButton);

        imgMonthsRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MonthsActivity.class);
                startActivity(intent);
            }
        });

        imgUserGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                return;
            }
        });

        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        ParAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

    }
}