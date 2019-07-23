package com.example.dayoff.employeeViewActivity;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.dayoff.MainActivity;
import com.example.dayoff.R;
import com.example.dayoff.model.EmpPager;
import com.example.dayoff.model.EmpSignUp;
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
import androidx.viewpager.widget.ViewPager;

public class EmpHomePageActivity extends AppCompatActivity {
    TabLayout tabLayoutEmp;
    ViewPager viewPagerEmp;
    public  String token="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_view);

        tabLayoutEmp=findViewById(R.id.tab_emp_view);
        viewPagerEmp=findViewById(R.id.view_pager_emp);
        tabLayoutEmp.addTab(tabLayoutEmp.newTab().setIcon(R.drawable.ic_event_note_black_24dp),0);
        tabLayoutEmp.addTab(tabLayoutEmp.newTab().setIcon(R.drawable.ic_format_list_bulleted_black_24dp),1);
        tabLayoutEmp.addTab(tabLayoutEmp.newTab().setIcon(R.drawable.ic_person_black_100dp),2);
        tabLayoutEmp.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               viewPagerEmp.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
       viewPagerEmp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutEmp));
       EmpPager pagerEmp=new EmpPager(getSupportFragmentManager(),tabLayoutEmp.getTabCount(), EmpHomePageActivity.this);
       viewPagerEmp.setAdapter(pagerEmp);
       viewPagerEmp.setCurrentItem(1);

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
                         token = task.getResult().getToken();

                        // Log and toast
                        //String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("tokennn", "TOKEN : "+token+" : TOKENID");
                        Toast.makeText(EmpHomePageActivity.this, token, Toast.LENGTH_SHORT).show();

                        SharedPreferences sharedPreferences = getSharedPreferences(EmpLoginActivity.myEmpPref,MODE_PRIVATE);
                        final String uid = sharedPreferences.getString(EmpLoginActivity.Empuid,null);
                        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("EmpSignUp");
                        databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                EmpSignUp empSignUp = dataSnapshot.getValue(EmpSignUp.class);
                                String img = empSignUp.getImageUrl();
                                String name = empSignUp.getName();
                                String email = empSignUp.getUsername();
                                String phone = empSignUp.getPhone();
                                String pass = empSignUp.getPassword();
                                String status = empSignUp.getStatus();
                                EmpSignUp empSignUp1 = new EmpSignUp(img,name, email,phone, pass,status,token);
                                databaseReference.child(uid).setValue(empSignUp1);
                    }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                });
    }
});
    }
}