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

public class UpdateUserInformation extends AppCompatActivity {
    private Button updateUserInformation;
    private EditText fullName, username, email;
    private TextView textError;

    private ImageView imgHomePage, imgMonthsRecord, imgUserGuide, imgSetting;
    private FloatingActionButton ParAddButton;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    public static final String LOGIN = "LOGIN";
    public static final String USERNAME = "USERNAME";
    public static final String FULLNAME = "FULLNAME";
    public static final String EMAIL = "EMAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_user_information_activity);
        getSupportActionBar().hide();

        //--References
        checkUserLogin();
        setupReference();
        setUp();
        setupParActions();
    }

    private void checkUserLogin() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        String login = prefs.getString(LOGIN, "false");
        if (login.equalsIgnoreCase("false")) {
            Intent intent = new Intent(UpdateUserInformation.this, LoginActivity.class);
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
                Intent intent = new Intent(UpdateUserInformation.this, MainActivity.class);
                startActivity(intent);
            }
        });

        imgMonthsRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateUserInformation.this, MonthsActivity.class);
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
                Intent intent = new Intent(UpdateUserInformation.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        ParAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateUserInformation.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUp() {
        updateUserInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((fullName.length() > 0) && (username.length() > 0) && (email.length() > 0)) {
                    if (prefs.getString(FULLNAME, "").equalsIgnoreCase(fullName.getText().toString()) &&
                            prefs.getString(USERNAME, "").equalsIgnoreCase(username.getText().toString()) &&
                            prefs.getString(EMAIL, "").equalsIgnoreCase(email.getText().toString())){
                        Toast.makeText(UpdateUserInformation.this, "لم يتم تغير المعومات!", Toast.LENGTH_SHORT).show();
                    } else {
                        updateUserInformation(fullName.getText().toString(),prefs.getString(USERNAME,""),username.getText().toString(),email.getText().toString());
                    }
                } else {
                    Toast.makeText(UpdateUserInformation.this, "أكمل المعلومات أولاً!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateUserInformation(String fullname, String username, String newusername, String email) {
        String url = "https://adam.s-matar.com/android-restAPI/updateuserinformation.php";
        RequestQueue queue = Volley.newRequestQueue(UpdateUserInformation.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("error").equalsIgnoreCase("true")) {
                        textError.setText("اسم المستخدم او البريد الإلكتروني مستخدم من قبل!");
                        Toast.makeText(UpdateUserInformation.this, "اسم المستخدم او البريد الإلكتروني مستخدم من قبل!", Toast.LENGTH_SHORT).show();
                    } else {
                        textError.setText("");
                        editor.putString(USERNAME, jsonObject.getString("username"));
                        editor.putString(FULLNAME, jsonObject.getString("fullname"));
                        editor.putString(EMAIL, jsonObject.getString("email"));
                        editor.commit();
                        Toast.makeText(UpdateUserInformation.this, "تم تحديث المعلومات بنجاح!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UpdateUserInformation.this, "" + error, Toast.LENGTH_SHORT).show();
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
                params.put("newusername", newusername);
                params.put("fullname", fullname);
                params.put("email", email);

                return params;
            }
        };
        queue.add(request);
    }


    //References
    private void setupReference() {
        updateUserInformation = findViewById(R.id.updateUserInformation);
        fullName = findViewById(R.id.fullName);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        textError = findViewById(R.id.error);

        fullName.setText(prefs.getString(FULLNAME, ""));
        username.setText(prefs.getString(USERNAME, ""));
        email.setText(prefs.getString(EMAIL, ""));
    }
}