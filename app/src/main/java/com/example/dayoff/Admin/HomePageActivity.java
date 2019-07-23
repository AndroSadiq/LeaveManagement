package com.example.dayoff.Admin;

import android.Manifest;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.dayoff.R;
import com.example.dayoff.employeeViewActivity.EmpHomePageActivity;
import com.example.dayoff.employeeViewActivity.EmpLoginActivity;
import com.example.dayoff.model.EmpSignUp;
import com.example.dayoff.model.Pager;
import com.example.dayoff.model.SignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

public class HomePageActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tabLayout=findViewById(R.id.tab);
        viewPager=findViewById(R.id.view_pager);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_group_black_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_event_available_black_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_settings_black_24dp));
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},101);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        Pager pager=new Pager(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pager);
        viewPager.setCurrentItem(1);
        //Retrieve the current registration token
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            //Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        final String token = task.getResult().getToken();

                        // Log and toast
                        //String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("tokennn", "TOKEN : "+token+" : TOKENID");
                        Toast.makeText(HomePageActivity.this, token, Toast.LENGTH_SHORT).show();

                        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.myPref,MODE_PRIVATE);
                        final String uid = sharedPreferences.getString(LoginActivity.uid,null);
                        /*String img = sharedPreferences.getString(LoginActivity.imageUrl,null);
                        String company = sharedPreferences.getString(LoginActivity.companyName,null);
                        String email = sharedPreferences.getString(LoginActivity.username,null);
                        String phone = sharedPreferences.getString(LoginActivity.phone,null);
                        String pass = sharedPreferences.getString(LoginActivity.password,null);*/
                         final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("AdminSignUp");
                        databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                SignUp signUp = dataSnapshot.getValue(SignUp.class);
                                String img = signUp.getImageUrl();
                                String company = signUp.getCompanyName();
                                String email = signUp.getUsername();
                                String phone = signUp.getPhone();
                                String pass = signUp.getPassword();

                                SignUp signUp1 = new SignUp(img,company, email,phone, pass,token);
                                databaseReference.child(uid).child("token").setValue(token);
                            }
                           @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }

    });
}
}
