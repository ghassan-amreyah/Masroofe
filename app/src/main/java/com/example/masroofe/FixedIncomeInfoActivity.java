package com.example.masroofe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FixedIncomeInfoActivity extends AppCompatActivity {

    private Button addIncomeBtn;
    private TextView nameOfUser;
    private EditText incomeAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fixed_income_info_activity);
        getSupportActionBar().hide();

        //References
        setupReference();
        addIcomeSeutp();
    }



    //--------------Methods---------------------------------------------------------

    //References
    private void setupReference() {
        nameOfUser = findViewById(R.id.nameOfUser);
        addIncomeBtn = findViewById(R.id.addIncomeBtn);
        incomeAmount = findViewById(R.id.incomeAmount);

    }

    private void addIcomeSeutp()
    {
        addIncomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

            }
        });
    }
}

