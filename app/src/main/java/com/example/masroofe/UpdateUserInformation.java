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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UpdateUserInformation extends AppCompatActivity {
    private Button updateUserInformation;
    private EditText fullName, username, email;
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
        checkUserLogin();
        setupReference();
        setUp();
        setupParActions();
    }

    private void checkUserLogin() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        String login = prefs.getString(LOGIN, "false");
        if (login.equalsIgnoreCase("false")){
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
                    Toast.makeText(UpdateUserInformation.this, "تم تحديث المعلومات!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpdateUserInformation.this, "أكمل المعلومات أولاً!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //References
    private void setupReference() {
        updateUserInformation = findViewById(R.id.updateUserInformation);
        fullName = findViewById(R.id.fullName);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);

        fullName.setText(prefs.getString(FULLNAME,""));
        username.setText(prefs.getString(USERNAME,""));
        email.setText(prefs.getString(EMAIL,""));
    }
}