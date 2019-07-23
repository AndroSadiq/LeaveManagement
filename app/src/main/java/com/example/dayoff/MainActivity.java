package com.example.dayoff;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dayoff.Admin.LoginActivity;
import com.example.dayoff.adapter.PagerImagesAdapter;
import com.example.dayoff.employeeViewActivity.EmpLoginActivity;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;
import spencerstudios.com.bungeelib.Bungee;

public class MainActivity extends AppCompatActivity {

    Button btnAdminPanel;
    Button btnEmpPanel;
    ViewPager viewPager;
    int[] layouts;
    TabLayout tabDots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},102);
        btnAdminPanel=findViewById(R.id.btn_admin);
        btnEmpPanel=findViewById(R.id.btn_employee);
        viewPager=findViewById(R.id.view_pager_main);
        layouts =new int[]{R.layout.image_fragment_one,R.layout.image_fragment_two,
                            R.layout.image_frag_three,R.layout.image_frag_four};
       viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int i, float v, int i1) {
           }
           @Override
           public void onPageSelected(int i) {
           }
           @Override
           public void onPageScrollStateChanged(int i) {
           }
       });
        PagerImagesAdapter pagerImagesAdapter=new PagerImagesAdapter(MainActivity.this,layouts);
        viewPager.setAdapter(pagerImagesAdapter);
        tabDots=findViewById(R.id.dots_tab);
        tabDots.setupWithViewPager(viewPager,true);
        btnAdminPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                Bungee.zoom(MainActivity.this);
            }
        });
        btnEmpPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, EmpLoginActivity.class);
                startActivity(intent);
                Bungee.zoom(MainActivity.this);
            }
        });
    }
}

