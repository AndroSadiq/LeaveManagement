package com.example.dayoff.Admin;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dayoff.R;
import com.example.dayoff.model.WorkingDays;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WorkingDaysActivity extends AppCompatActivity {

    CheckBox chbMon,chbTue,chbWed,chbThu,chbFri,chbSat,chbSun;
    Button btnSave;
    String mon,tue,wed,thu,fri,sat,sun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working_days);

        chbMon = findViewById(R.id.chk_mon);
        chbTue = findViewById(R.id.chk_tue);
        chbWed = findViewById(R.id.chk_wed);
        chbThu = findViewById(R.id.chk_thu);
        chbFri = findViewById(R.id.chk_fri);
        chbSat = findViewById(R.id.chk_sat);
        chbSun = findViewById(R.id.chk_sun);
        btnSave = findViewById(R.id.btn_save_working_days);

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("WorkingDays");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                WorkingDays workingDays = dataSnapshot.getValue(WorkingDays.class);

                if (workingDays.getMon().equals(""))
                {
                    chbMon.setChecked(false);
                }
                if (workingDays.getTue().equals(""))
                {
                    chbTue.setChecked(false);
                }
                if (workingDays.getWed().equals(""))
                {
                    chbWed.setChecked(false);
                }
                if (workingDays.getThu().equals(""))
                {
                    chbThu.setChecked(false);
                }
                if (workingDays.getFri().equals(""))
                {
                    chbFri.setChecked(false);
                }
                if (workingDays.getSat().equals(""))
                {
                    chbSat.setChecked(false);
                }
                if (workingDays.getSun().equals(""))
                {
                    chbSun.setChecked(false);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chbMon.isChecked())
                {
                    chbMon.setChecked(true);
                    mon = "Mon";
                }
                else
                {
                    chbMon.setChecked(false);
                    mon = "";
                }

                if (chbTue.isChecked())
                {
                    chbTue.setChecked(true);
                    tue = "Tue";
                }
                else
                {
                    chbTue.setChecked(false);
                    tue = "";
                }

                if (chbWed.isChecked())
                {
                    chbWed.setChecked(true);
                    wed = "Wed";
                }
                else
                {
                    chbWed.setChecked(false);
                    wed = "";
                }

                if (chbThu.isChecked())
                {
                    chbThu.setChecked(true);
                    thu = "Thu";
                }
                else
                {
                    chbThu.setChecked(false);
                    thu = "";
                }

                if (chbFri.isChecked())
                {
                    chbFri.setChecked(true);
                    fri = "Fri";
                }
                else
                {
                    chbFri.setChecked(false);
                    fri = "";
                }

                if (chbSat.isChecked())
                {
                    chbSat.setChecked(true);
                    sat = "Sat";
                }
                else
                {
                    chbSat.setChecked(false);
                    sat = "";
                }

                if (chbSun.isChecked())
                {
                    chbSun.setChecked(true);
                    sun = "Sun";
                }
                else
                {
                    chbSun.setChecked(false);
                    sun = "";
                }
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("WorkingDays");
                WorkingDays workingDays = new WorkingDays(mon,tue,wed,thu,fri,sat,sun);
                databaseReference.setValue(workingDays);
                Toast.makeText(WorkingDaysActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
