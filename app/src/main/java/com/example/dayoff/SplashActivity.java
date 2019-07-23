package com.example.dayoff;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dayoff.Admin.HomePageActivity;
import com.example.dayoff.Admin.LoginActivity;
import com.example.dayoff.employeeViewActivity.EmpHomePageActivity;
import com.example.dayoff.employeeViewActivity.EmpLoginActivity;

import static android.os.SystemClock.sleep;

public class SplashActivity extends AppCompatActivity {

    String user = "";
    String empUser ="";

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences Emppreferences = getSharedPreferences(EmpLoginActivity.myEmpPref,MODE_PRIVATE);
        empUser = Emppreferences.getString(EmpLoginActivity.Empusername,"");
        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.myPref,MODE_PRIVATE);
        user = sharedPreferences.getString(LoginActivity.username,"");
        Thread SplashScreen = new Thread(new Runnable() {
            @Override
            public void run() {
                sleep(3000);
                   if (user.equals("")) {
                       if (empUser.equals("")) {
                           Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                           startActivity(intent);
                           finish();
                       } else {
                           Intent intent = new Intent(SplashActivity.this, EmpHomePageActivity.class);
                           startActivity(intent);
                           finish();
                       }
                   }
                   else {
                        Intent intent = new Intent(SplashActivity.this, HomePageActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

       });

        SplashScreen.start();

    }

}

