package com.example.dayoff.Admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.dayoff.R;
import com.example.dayoff.adapter.MyHolidayAdapter;
import com.example.dayoff.model.HolidayListView;
import com.example.dayoff.model.HolidayName;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import spencerstudios.com.bungeelib.Bungee;

public class HolidaysActivity extends AppCompatActivity {

    MyHolidayAdapter myHolidayAdapter;
    ListView listView;
    ArrayList<String> keyList = new ArrayList<>();
    ArrayList<String> nameList = new ArrayList();
    ArrayList<String> starDateList = new ArrayList();
    ArrayList<String> endDateList = new ArrayList();
    Button btnAddHoliday;
    ArrayList<HolidayListView> holidayArrayList = new ArrayList();
    Toolbar toolbar;
    @Override
    public boolean navigateUpTo(Intent upIntent) {
        onBackPressed();
        return true;
    }
     public HolidaysActivity()
     {

     }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holidays);

        listView=findViewById(R.id.lv_holiday);
        toolbar =findViewById(R.id.toolbar_holidays);
        setSupportActionBar(toolbar);
        btnAddHoliday=findViewById(R.id.btn_add_holiday);
        btnAddHoliday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HolidaysActivity.this,AddHolidayActivity.class);
                startActivity(intent);
                finish();
            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("OfficialHolidays");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  holidayArrayList.clear();
                  for (DataSnapshot snapshot:dataSnapshot.getChildren())
                  {
                      HolidayName holidayName = snapshot.getValue(HolidayName.class);
                      keyList.add(snapshot.getKey());
                      nameList.add(holidayName.getHolidayName());
                      starDateList.add(holidayName.getStartDate());
                      endDateList.add(holidayName.getEndDate());
                      myHolidayAdapter.notifyDataSetChanged();
                  }
               for (int i=0;i<nameList.size();i++)
               {
                   HolidayListView holiday = new HolidayListView(R.drawable.ic_event_busy_black_24dp,nameList.get(i),starDateList.get(i),endDateList.get(i));
                   holidayArrayList.add(holiday);
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
         myHolidayAdapter = new MyHolidayAdapter(holidayArrayList,HolidaysActivity.this,keyList);
         listView.setAdapter(myHolidayAdapter);
         myHolidayAdapter.notifyDataSetChanged();
         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                 AlertDialog.Builder builder=new AlertDialog.Builder(HolidaysActivity.this);
                 builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         dialog.dismiss();
                     }
                 });
                 builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         String key = keyList.get(position);
                         holidayArrayList.remove(position);
                         DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                         databaseReference.child("OfficialHolidays").child(key).removeValue();
                         myHolidayAdapter.notifyDataSetChanged();
                         Intent intent = new Intent(HolidaysActivity.this,HolidaysActivity.class);
                         startActivity(intent);
                         finish();
                         Bungee.zoom(HolidaysActivity.this);
                         dialog.dismiss();
                     }
                 });
                 AlertDialog dialog=builder.create();
                 dialog.setTitle("Are You Sure ?");
                 dialog.show();
                 dialog.setCanceledOnTouchOutside(false);
             }
         });
    }
}
