package com.example.masroofe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AddActivity extends AppCompatActivity {

    private Button defineBtn, expensesBtn, revenueBtn;

    private ImageView imgHomePage, imgUserGuide, imgSetting, imgMonthsRecord;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    public static final String LOGIN = "LOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);
        getSupportActionBar().hide();
        checkUserLogin();
        setupParActions();
        setupReference();
        Setup();
    }

    private void Setup() {
        defineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddActivity.this, DefineActivity.class);
                startActivity(intent);
            }
        });
        expensesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddActivity.this, AddExpActivity.class);
                startActivity(intent);
            }
        });
        revenueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddActivity.this, AddRevActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupReference() {
        defineBtn = findViewById(R.id.btnDefine);
        expensesBtn = findViewById(R.id.btnExp);
        revenueBtn = findViewById(R.id.btnRev);
    }


    private void checkUserLogin() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        String login = prefs.getString(LOGIN, "false");
        if (login.equalsIgnoreCase("false")){
            Intent intent = new Intent(AddActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void setupParActions() {
        imgHomePage = findViewById(R.id.imgHomePage);
        imgUserGuide = findViewById(R.id.imgUserGuide);
        imgSetting = findViewById(R.id.imgSetting);
        imgMonthsRecord = findViewById(R.id.imgMonthsRecord);

        imgHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        imgMonthsRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddActivity.this, MonthsActivity.class);
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
                Intent intent = new Intent(AddActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}