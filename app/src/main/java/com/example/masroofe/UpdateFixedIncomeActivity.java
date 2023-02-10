package com.example.masroofe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class UpdateFixedIncomeActivity extends AppCompatActivity {

    private ImageView imgHomePage, imgMonthsRecord, imgUserGuide, imgSetting;
    private FloatingActionButton ParAddButton;

    private Button addIncomeBtn;
    private EditText incomeAmount;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    public static final String LOGIN = "LOGIN";
    public static final String USERNAME = "USERNAME";
    public static final String FIXEDINCOME = "FIXEDINCOME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_fixed_income);
        getSupportActionBar().hide();
        checkUserLogin();
        setupParActions();
        //References
        setupReference();
        addIncomeSetup();
    }

    private void checkUserLogin() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        String login = prefs.getString(LOGIN, "false");
        if (login.equalsIgnoreCase("false")){
            Intent intent = new Intent(UpdateFixedIncomeActivity.this, LoginActivity.class);
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
                Intent intent = new Intent(UpdateFixedIncomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        imgMonthsRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateFixedIncomeActivity.this, MonthsActivity.class);
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
                Intent intent = new Intent(UpdateFixedIncomeActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        ParAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateFixedIncomeActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupReference() {
        addIncomeBtn = findViewById(R.id.addIncomeBtn);
        incomeAmount = findViewById(R.id.incomeAmount);

        if(!prefs.getString(FIXEDINCOME,"").equalsIgnoreCase("null")){
            incomeAmount.setText(prefs.getString(FIXEDINCOME,""));
        }
    }
    private void addIncomeSetup() {
        addIncomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int amount = Integer.parseInt(incomeAmount.getText().toString());
                if(amount<1){
                    Toast.makeText(UpdateFixedIncomeActivity.this, "اضف قيمة الراتب الثابت أولاً!", Toast.LENGTH_SHORT).show();
                }else{
                    addIncome(amount);
                }
            }
        });
    }


    private void addIncome(int amount){
        String url = "https://adam.s-matar.com/android-restAPI/addfixedincome.php";
        RequestQueue queue = Volley.newRequestQueue(UpdateFixedIncomeActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("error").equalsIgnoreCase("true")) {
                        Toast.makeText(UpdateFixedIncomeActivity.this, "حدث خطأ!", Toast.LENGTH_SHORT).show();
                    } else {
                        editor.putString(FIXEDINCOME, String.valueOf(amount));
                        editor.commit();
                        Toast.makeText(UpdateFixedIncomeActivity.this, "تم إضافة الراتب الثابت بنجاح!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateFixedIncomeActivity.this, SettingsActivity.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UpdateFixedIncomeActivity.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("username", prefs.getString(USERNAME, ""));
                params.put("fixedincome", String.valueOf(amount));

                return params;
            }
        };
        queue.add(request);
    }
}