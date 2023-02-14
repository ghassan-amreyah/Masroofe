package com.example.masroofe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MonthsActivity extends AppCompatActivity {

    private Spinner year, month, accountType;
    private Button search;
    private TextView recordResult;
    private List<Record> items = new ArrayList<>();
    private RecyclerView recycler;

    private ImageView imgHomePage, imgUserGuide, imgSetting;
    private FloatingActionButton ParAddButton;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    public static final String LOGIN = "LOGIN";
    public static final String USERNAME = "USERNAME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.months_activity);
        getSupportActionBar().hide();
        checkUserLogin();
        setupParActions();
        setupReference();
        setUp();
    }

    private void setUp() {
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Year = year.getSelectedItem().toString();
                String Month = month.getSelectedItem().toString();
                String Table = accountType.getSelectedItem().toString();
                if(Table.equals("مصروف")){
                    loadItems("expenses", Year, Month, prefs.getString(USERNAME, ""));
                }
                else if (Table.equals("إيراد / دخل")){
                    loadItems("revenues", Year, Month, prefs.getString(USERNAME, ""));
                }
            }
        });
    }

    private void loadItems(String table, String year, String month, String username) {
        String url = "https://adam.s-matar.com/android-restAPI/getrecords.php";
        RequestQueue queue = Volley.newRequestQueue(MonthsActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("error").equalsIgnoreCase("true")) {
                        Toast.makeText(MonthsActivity.this, "حدث خطأ!", Toast.LENGTH_SHORT).show();
                    } else {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i<array.length(); i++){

                            JSONObject object = array.getJSONObject(i);


                            String accountname = object.getString("accountname");
                            String amount = object.getString("amount");
                            String date = object.getString("date");
                            Record record = new Record(accountname, amount,date);
                            items.add(record);
                        }
                        Toast.makeText(MonthsActivity.this, "تم إنهاء بنجاح", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MonthsActivity.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("username", username);
                params.put("year", year);
                params.put("month", month);
                params.put("table", table);
                return params;
            }
        };
        queue.add(request);
    }

    private void setupReference() {
        recordResult = findViewById(R.id.record_result);
        recycler = findViewById(R.id.record_recycler);
        search = findViewById(R.id.btnSelectMonthYear);
        year = findViewById(R.id.Year);
        month = findViewById(R.id.Month);
        accountType = findViewById(R.id.AccountType);
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private void checkUserLogin() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        String login = prefs.getString(LOGIN, "false");
        if (login.equalsIgnoreCase("false")) {
            Intent intent = new Intent(MonthsActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void setupParActions() {
        imgHomePage = findViewById(R.id.imgHomePage);
        imgUserGuide = findViewById(R.id.imgUserGuide);
        imgSetting = findViewById(R.id.imgSetting);
        ParAddButton = findViewById(R.id.ParAddButton);

        imgHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MonthsActivity.this, MainActivity.class);
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
                Intent intent = new Intent(MonthsActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        ParAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MonthsActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }
}