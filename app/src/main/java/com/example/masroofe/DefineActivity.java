package com.example.masroofe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DefineActivity extends AppCompatActivity {

    private ImageView imgHomePage, imgMonthsRecord, imgUserGuide, imgSetting;
    private FloatingActionButton ParAddButton;
    private Spinner spinner;
    private EditText accountName;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    public static final String LOGIN = "LOGIN";
    public static final String USERNAME = "USERNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.define_activity);
        getSupportActionBar().hide();
        checkUserLogin();
        setupParActions();
        setUp();
    }

    public void btn_defineActivity_OnClick(View view) {
        String type = spinner.getSelectedItem().toString();
        String name = accountName.getText().toString();
        if (name.trim().length()>0){
            if(type.equals("مصروف")){
                AddAccount(name,"expenses",prefs.getString(USERNAME,""));
            }
            else if (type.equals("إيراد / دخل")){
                AddAccount(name,"revenues",prefs.getString(USERNAME,""));
            }
        }else{
            Toast.makeText(this, "أدخل اسم الحساب", Toast.LENGTH_SHORT).show();
        }
    }

    private void AddAccount(String name, String table, String username) {
        String url = "https://adam.s-matar.com/android-restAPI/addaccounts.php";
        RequestQueue queue = Volley.newRequestQueue(DefineActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getString("error").equalsIgnoreCase("true")){
                        Toast.makeText(DefineActivity.this, "الحساب موجود من قبل!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(DefineActivity.this, "تم إضافة الحساب بنجاح", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DefineActivity.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String,String>();

                params.put("username", username);
                params.put("name", name);
                params.put("table", table);
                return params;
            }
        };
        queue.add(request);
    }

    private void setUp() {
        spinner = findViewById(R.id.AccountType);
        accountName = findViewById(R.id.AccountName);
    }


    private void checkUserLogin() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        String login = prefs.getString(LOGIN, "false");
        if (login.equalsIgnoreCase("false")){
            Intent intent = new Intent(DefineActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void setupParActions() {
        imgHomePage = findViewById(R.id.imgHomePage);
        imgMonthsRecord = findViewById(R.id.imgMonthsRecord);
        imgUserGuide = findViewById(R.id.imgUserGuide);
        imgSetting = findViewById(R.id.imgSetting);
        ParAddButton = findViewById(R.id.ParAddButton);

        imgHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DefineActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        imgMonthsRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DefineActivity.this, MonthsActivity.class);
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
                Intent intent = new Intent(DefineActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        ParAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DefineActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

}