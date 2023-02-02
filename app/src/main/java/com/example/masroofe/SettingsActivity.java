package com.example.masroofe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SettingsActivity extends AppCompatActivity {
    private Button updateUserInformationBtn, updateUserPasswordBtn, fixedIncome;
    private ImageView imgHomePage, imgMonthsRecord, imgUserGuide;
    private FloatingActionButton ParAddButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportActionBar().hide();
        setupParActions();
        setupReference();
        setUp();
    }

    private void setupParActions() {
        imgHomePage = findViewById(R.id.imgHomePage);
        imgMonthsRecord = findViewById(R.id.imgMonthsRecord);
        imgUserGuide = findViewById(R.id.imgUserGuide);
        ParAddButton = findViewById(R.id.ParAddButton);

        imgHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        imgMonthsRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, MonthsActivity.class);
                startActivity(intent);
            }
        });

        imgUserGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                return;
            }
        });


        ParAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUp() {
        updateUserInformationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent UpdateUserInformation = new Intent(SettingsActivity.this, UpdateUserInformation.class);
                startActivity(UpdateUserInformation);
            }
        });

        updateUserPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent UpdateUserPassword = new Intent(SettingsActivity.this, UpdateUserPassword.class);
                startActivity(UpdateUserPassword);
            }
        });

        fixedIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fixedIncome = new Intent(SettingsActivity.this, FixedIncomeInfoActivity.class);
                startActivity(fixedIncome);
            }
        });
    }

    private void setupReference() {
        updateUserInformationBtn = findViewById(R.id.updateUserInformation);
        updateUserPasswordBtn = findViewById(R.id.updateUserPassword);
        fixedIncome = findViewById(R.id.fixedIncome);
    }
}