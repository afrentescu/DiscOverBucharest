package com.example.user.discoverbucharest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class DailyPlanActivity extends AppCompatActivity {
    private Button btnday1, btnday2, btnday3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dailyplan);
        btnday1 = findViewById(R.id.btnDayOne);

        btnday1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DayOneActivity.class);
                startActivity(intent);
            }
        });
        btnday2 =findViewById(R.id.btnDayTwo);
     btnday2.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(getApplicationContext(), DayTwoActivity.class);
             startActivity(intent);
         }
     });

        btnday3 =findViewById(R.id.btnDayTree);
        btnday3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DayTreeActivity.class);
                startActivity(intent);
            }
        });
    }
}
