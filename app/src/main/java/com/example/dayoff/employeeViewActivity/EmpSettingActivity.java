package com.example.dayoff.employeeViewActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.dayoff.Admin.LoginActivity;
import com.example.dayoff.MainActivity;
import com.example.dayoff.R;
import com.example.dayoff.adapter.EmpAdapter;
import com.example.dayoff.model.EmpSetting;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class EmpSettingActivity extends AppCompatActivity {

    ListView empListView;
    Toolbar empToolbar;
    ArrayList<EmpSetting> empSettingArrayList=new ArrayList<>();
    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_setting);
        empListView=findViewById(R.id.lv_emp_setting);
        empToolbar=findViewById(R.id.toolbar_emp_setting);
        setSupportActionBar(empToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        EmpSetting empSetting1=new EmpSetting(R.drawable.edit_profile_512,"Edit Profile");
        EmpSetting empSetting2=new EmpSetting(R.drawable.change_password_512,"Change Password");
        EmpSetting empSetting3=new EmpSetting(R.drawable.logout_512,"Log Out");
        empSettingArrayList.add(empSetting1);
        empSettingArrayList.add(empSetting2);
        empSettingArrayList.add(empSetting3);
        EmpAdapter empAdapter=new EmpAdapter(empSettingArrayList, EmpSettingActivity.this);
        empListView.setAdapter(empAdapter);
        empListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                    {
                       Intent intent = new Intent(EmpSettingActivity.this, EmpEditProfileActivity.class);
                       startActivity(intent);
                       break;
                    }

                    case 1:
                    {
                        Intent intent = new Intent(EmpSettingActivity.this, EmpChangePasswordActivity.class);
                        startActivity(intent);
                        break;
                    }

                    case 2:
                    {
                        Intent intent = new Intent(EmpSettingActivity.this, MainActivity.class);
                        startActivity(intent);
                        SharedPreferences sharedPreferences = getSharedPreferences(EmpLoginActivity.myEmpPref,MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString(EmpLoginActivity.Empusername,"");
                        editor.putString(EmpLoginActivity.Emppassword,"");
                        editor.putString(EmpLoginActivity.Empuid,"");
                        editor.putString(EmpLoginActivity.EmpimageUrl,"");
                        editor.putString(EmpLoginActivity.Empname,"");
                        editor.putString(EmpLoginActivity.Empphone,"");
                        editor.apply();
                        finishAffinity();
                    }
                }
            }
        });

    }


}
