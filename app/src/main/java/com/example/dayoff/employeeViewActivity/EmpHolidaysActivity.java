package com.example.dayoff.employeeViewActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.dayoff.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EmpHolidaysActivity extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    public boolean navigateUpTo(Intent upIntent) {
        onBackPressed();
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_holidays);

        toolbar =findViewById(R.id.toolbar_holidays);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }
}
