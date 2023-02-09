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

public class SignupActivity extends AppCompatActivity {

    private Button finishBtn, nextBtn;
    private EditText fullName, username, email, password, repeatPassword;
    private TextView textError;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    public static final String LOGIN = "LOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        getSupportActionBar().hide();

        //--References
        checkUserLogin();
        setupReference();
        finishSetup();
        nextSetup();
    }

    private void checkUserLogin() {

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        String login = prefs.getString(LOGIN, "false");
        if (login.equalsIgnoreCase("true"))
        {
            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        checkUserLogin();
    }
    //--------------Methods-----------------------------------------------------------

    //References
    private void setupReference() {
        finishBtn = findViewById(R.id.finishBtnSignup);
        nextBtn = findViewById(R.id.nextBtnSignup);
        fullName = findViewById(R.id.fullName);
        username = findViewById(R.id.username);
        email = findViewById(R.id.emailSignup);
        password = findViewById(R.id.passwordSignup);
        repeatPassword = findViewById(R.id.passwordRepeatSignup);
    }

    private void finishSetup() {
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (fullName.length() > 0 && username.length() > 0 && email.length() > 0 && password.length() > 0&& repeatPassword.length() > 0 )
                {
                    SignupUser(username.getText().toString(), password.getText().toString(), fullName.getText().toString(), email.getText().toString());
                }
                else
                {
                    Toast.makeText(SignupActivity.this, "أملئ جميع الحقول\n وحاول مرة أخرى!", Toast.LENGTH_SHORT).show();
                    textError.setText("أملئ جميع الحقول وحاول مرة أخرى!");
                }
            }
        });
}


    private void nextSetup() {
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void SignupUser(String username, String password, String fullName, String email)
    {
        String url = "http://web1190759.studentswebprojects.ritaj.ps/android-restAPI/sinup.php";
        RequestQueue queue = Volley.newRequestQueue(SignupActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try
                {
                    JSONObject jsonObject = new JSONObject(response);

                    if(jsonObject.getString("error").equalsIgnoreCase("true"))
                    {
                        textError.setText("اسم المستخدم أو البريد الإلكتروني موجود من قبل!");

                        Toast.makeText(SignupActivity.this, "اسم المستخدم أو البريد الإلكتروني موجود من قبل!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        editor.putString(LOGIN, "true");
                        editor.commit();
                        Toast.makeText(SignupActivity.this, "تم إنشاء الحساب بنجاح", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
                catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(SignupActivity.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public String getBodyContentType()
            {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String,String>();

                params.put("username", username);
                params.put("password", password);
                params.put("fulName", fullName);
                params.put("email", email);
                return params;
            }
        };
        queue.add(request);
    }
}



