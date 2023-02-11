package com.example.masroofe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FixedIncomeInfoActivity extends AppCompatActivity {

    private Button addIncomeBtn;
    private TextView nameOfUser;
    private EditText incomeAmount;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    public static final String LOGIN = "LOGIN";
    public static final String USERNAME = "USERNAME";
    public static final String FULLNAME = "FULLNAME";
    public static final String FIXEDINCOME = "FIXEDINCOME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fixed_income_info_activity);
        getSupportActionBar().hide();
        //References
        setupReference();
        addIncomeSetup();
    }

    private void checkUserLogin() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
        String login = prefs.getString(LOGIN, "false");
        if (login.equalsIgnoreCase("true")) {
            Intent intent = new Intent(FixedIncomeInfoActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkUserLogin();
    }

    //--------------Methods---------------------------------------------------------

    //References
    private void setupReference() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
        nameOfUser = findViewById(R.id.nameOfUser);
        addIncomeBtn = findViewById(R.id.addIncomeBtn);
        incomeAmount = findViewById(R.id.incomeAmount);

        nameOfUser.setText("مرحباً بك " + prefs.getString(FULLNAME, ""));
    }

    private void addIncomeSetup() {
        addIncomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int amount = Integer.parseInt(incomeAmount.getText().toString());
                if (amount < 1) {
                    Toast.makeText(FixedIncomeInfoActivity.this, "اضف قيمة الراتب الثابت أولاً!", Toast.LENGTH_SHORT).show();
                } else {
                    addIncome(amount);
                }
            }
        });
    }

    private void addIncome(int amount) {
        String url = "https://adam.s-matar.com/android-restAPI/addfixedincome.php";
        RequestQueue queue = Volley.newRequestQueue(FixedIncomeInfoActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("error").equalsIgnoreCase("true")) {
                        Toast.makeText(FixedIncomeInfoActivity.this, "حدث خطأ!", Toast.LENGTH_SHORT).show();
                    } else {
                        editor.putString(FIXEDINCOME, String.valueOf(amount));
                        editor.commit();
                        Toast.makeText(FixedIncomeInfoActivity.this, "تم إضافة الراتب الثابت بنجاح!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(FixedIncomeInfoActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FixedIncomeInfoActivity.this, "" + error, Toast.LENGTH_SHORT).show();
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

