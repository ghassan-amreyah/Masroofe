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

public class UpdateUserPassword extends AppCompatActivity {

    private Button updateUserPassword;
    private EditText currentPassword, newPassword, repeatNewPassword;
    private ImageView imgHomePage, imgMonthsRecord, imgUserGuide, imgSetting;
    private FloatingActionButton ParAddButton;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    public static final String LOGIN = "LOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_user_password_activity);
        getSupportActionBar().hide();
        checkUserLogin();
        setupParActions();
        setupReference();
        setUp();
    }

    private void checkUserLogin() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        String login = prefs.getString(LOGIN, "false");
        if (login.equalsIgnoreCase("false")){
            Intent intent = new Intent(UpdateUserPassword.this, LoginActivity.class);
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
                Intent intent = new Intent(UpdateUserPassword.this, MainActivity.class);
                startActivity(intent);
            }
        });

        imgMonthsRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateUserPassword.this, MonthsActivity.class);
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
                Intent intent = new Intent(UpdateUserPassword.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        ParAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateUserPassword.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUp() {

        updateUserPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPassword.length() > 0 && newPassword.length() > 0 && repeatNewPassword.length() > 0) {
                    if (currentPassword.getText().toString().equals(null)) {
                        if (newPassword.getText().toString().equals(repeatNewPassword.getText().toString())) {
                            Toast.makeText(UpdateUserPassword.this, "تم تحديث كلمة السر!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(UpdateUserPassword.this, "كلمة السر الجديدة غير مطابقة!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(UpdateUserPassword.this, "كلمة السر الحالية غير صحيحة", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UpdateUserPassword.this, "أكمل المعلومات أولاً!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void setupReference() {
        currentPassword = findViewById(R.id.currentPassword);
        newPassword = findViewById(R.id.newPassword);
        repeatNewPassword = findViewById(R.id.repeatNewPassword);
        updateUserPassword = findViewById(R.id.updateUserPassword);
    }
}