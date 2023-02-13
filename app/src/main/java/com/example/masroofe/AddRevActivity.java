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
import android.widget.Spinner;
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

import java.util.HashMap;
import java.util.Map;

public class AddRevActivity extends AppCompatActivity {
    private Spinner accountsSpinner;
    private EditText expenseAmount;
    private Button addAmountButton;

    private ImageView imgHomePage, imgMonthsRecord, imgUserGuide, imgSetting;
    private FloatingActionButton ParAddButton;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    public static final String LOGIN = "LOGIN";
    public static final String USERNAME = "USERNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_rev_activity);
        getSupportActionBar().hide();
        checkUserLogin();
        setupParActions();
        setupReference();
        setUp();
    }

    private void setupReference() {
        accountsSpinner = findViewById(R.id.AccountsSpinner);
        expenseAmount = findViewById(R.id.incomeAmount);
        addAmountButton = findViewById(R.id.AddAmount);
    }

    private void setUp() {
        fillSpinner(prefs.getString(USERNAME, ""));
        addAmountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expenseAmount.length() > 0) {
                    double amount = Double.parseDouble(expenseAmount.getText().toString());
                    if (amount > 0) {
                        // هان رح نعمل الحركة بس بعدين
                    } else {
                        Toast.makeText(AddRevActivity.this, "اضف قيمة صحيحة!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddRevActivity.this, "اضف القيمة أولا!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void fillSpinner(String username) {
        String url = "https://adam.s-matar.com/android-restAPI/getaccounts.php";
        RequestQueue queue = Volley.newRequestQueue(AddRevActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("error").equalsIgnoreCase("true")) {
                        Toast.makeText(AddRevActivity.this, "حدث خطأ!", Toast.LENGTH_SHORT).show();
                    } else {
                        JSONArray array = new JSONArray(jsonObject);
                        for (int i = 0; i < jsonObject.length(); i++) {
                            array.getString(i); //هان بجيب كل اسماء الاكاونتات تبعات المستخدم
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddRevActivity.this, "حدث خطأ!", Toast.LENGTH_SHORT).show();
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
                params.put("table", "revenues");
                return params;
            }
        };
        queue.add(request);
    }

    private void checkUserLogin() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        String login = prefs.getString(LOGIN, "false");
        if (login.equalsIgnoreCase("false")){
            Intent intent = new Intent(AddRevActivity.this, LoginActivity.class);
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
                Intent intent = new Intent(AddRevActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        imgMonthsRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddRevActivity.this, MonthsActivity.class);
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
                Intent intent = new Intent(AddRevActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        ParAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddRevActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }
}