package com.example.dayoff.Admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.dayoff.MainActivity;
import com.example.dayoff.R;
import com.example.dayoff.adapter.SettingAdapter;
import com.example.dayoff.model.Setting;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SettingsActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    ArrayList<Setting> arrayList=new ArrayList<>();

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        listView=findViewById(R.id.lv_setting);
        Setting s1=new Setting(R.drawable.working_days_512,"Working Days");
        Setting s2=new Setting(R.drawable.leave_type_512,"Leave Types");
        Setting s3=new Setting(R.drawable.edit_profile_512,"Edit Profile");
        Setting s4=new Setting(R.drawable.change_password_512,"Change Password");
        Setting s5=new Setting(R.drawable.logout_512,"Log Out");
        arrayList.add(s1);
        arrayList.add(s2);
        arrayList.add(s3);
        arrayList.add(s4);
        arrayList.add(s5);
        SettingAdapter settingAdapter=new SettingAdapter(arrayList,SettingsActivity.this);
        listView.setAdapter(settingAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                    {
                        Intent intent=new Intent(SettingsActivity.this,WorkingDaysActivity.class);
                        startActivity(intent);
                        break;
                    }

                    case 1:
                    {
                        Intent intent=new Intent(SettingsActivity.this, LeaveTypesActivity.class);
                        startActivity(intent);
                        break;
                    }

                    case 2:
                    {
                        Intent intent=new Intent(SettingsActivity.this,EditProfileActivity.class);
                        startActivity(intent);
                        break;
                    }

                    case 3:
                    {
                        Intent intent=new Intent(SettingsActivity.this,ChangePasswordActivity.class);
                        startActivity(intent);
                        break;
                    }

                    case 4:
                    {
                      Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                      startActivity(intent);

                        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.myPref,MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString(LoginActivity.username,"");
                        editor.putString(LoginActivity.password,"");
                        editor.putString(LoginActivity.uid,"");
                        editor.putString(LoginActivity.imageUrl,"");
                        editor.putString(LoginActivity.companyName,"");
                        editor.putString(LoginActivity.phone,"");
                        editor.apply();
                        finishAffinity();

                    }
                }
            }

        });

    }
}
