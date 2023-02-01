package com.example.masroofe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SignupActivity extends AppCompatActivity {

    private Button finishBtn, nextBtn;
    private EditText fullName, dateBirth, username, password, repeatPassword;
    private int day, month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        getSupportActionBar().hide();

        //---References-----------------------------
        setupReference();

        calendarSetup();
        finishSetup();
        nextSetup();

    }


    //--------------Methods-----------------------------------------------------------

    //References
    private void setupReference() {
        finishBtn = findViewById(R.id.finishBtnSignup);
        nextBtn = findViewById(R.id.nextBtnSignup);
        dateBirth = findViewById(R.id.birthDay);
        fullName = findViewById(R.id.fullName);
        username = findViewById(R.id.email);
        password = findViewById(R.id.password);
        repeatPassword = findViewById(R.id.passwordRepeatSignup);
    }

    //Calendar
    private void calendarSetup() {
        Calendar calendarBirthDay = Calendar.getInstance();

        dateBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = calendarBirthDay.get(Calendar.YEAR);
                month = calendarBirthDay.get(Calendar.MONTH);
                day = calendarBirthDay.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePicker = new DatePickerDialog(SignupActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        dateBirth.setText(SimpleDateFormat.getDateInstance().format(calendarBirthDay.getTime()));
                    }
                }, year, month, day);

                datePicker.show();
            }
        });
    }


    private void finishSetup() {
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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


}



