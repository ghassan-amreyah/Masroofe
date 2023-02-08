package com.example.masroofe;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn, signUpBtn;
    private EditText email, password;
    private TextView textError;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    public static final String LOGIN = "LOGIN";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String FULLNAME = "FULLNAME";
    public static final String EMAIL = "EMAIL";
    public static final String BIRTHDATE = "BIRTHDATE";
    public static final String FIXEDINCOME = "FIXEDINCOME";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        getSupportActionBar().hide();

        //References
        setupReference();
        loginSetup();
        signupSetup();
    }


    //--------------Methods---------------------------------------------------------

    //References
    private void setupReference() {
        email = findViewById(R.id.email);
        textError = findViewById(R.id.error);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        signUpBtn = findViewById(R.id.signUpBtnInLoginActivity);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }


    private void loginSetup() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (email.length() > 0 && password.length() > 0)
                {
                    LoginUser(email.getText().toString(), password.getText().toString());
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "أملئ جميع الحقول،\n وحاول مرة أخرى", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void LoginUser(String username, String password) {
        String url = "http://web1190759.studentswebprojects.ritaj.ps/android-restAPI/userlogin.php";
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getString("error").equalsIgnoreCase("true")){
                        textError.setText("البيانات غير صحيحة!\nحاول مرة أخرى!");

                        Toast.makeText(LoginActivity.this, "البيانات غير صحيحة!\n حاول مرة أخرى!", Toast.LENGTH_SHORT).show();
                    }else{
                        editor.putString(LOGIN, "true");
                        editor.putString(USERNAME,jsonObject.getString("username"));
                        editor.putString(PASSWORD,jsonObject.getString("password"));
                        editor.putString(FULLNAME,jsonObject.getString("fullname"));
                        editor.putString(BIRTHDATE,jsonObject.getString("birthdate"));
                        editor.putString(EMAIL,jsonObject.getString("email"));
                        editor.putString(FIXEDINCOME,jsonObject.getString("fixedincome"));
                        editor.commit();
                        Toast.makeText(LoginActivity.this, "تم الدخول بنجاح", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "" + error, Toast.LENGTH_SHORT).show();
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
                params.put("password", password);
                return params;
            }
        };
        queue.add(request);
    }

    private void signupSetup() {
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}