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
import com.example.dayoff.adapter.MyLeaveTypesAdapter;
import com.example.dayoff.model.LeaveType;
import com.example.dayoff.model.LeavesTypeListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import spencerstudios.com.bungeelib.Bungee;

public class LeaveTypesActivity extends AppCompatActivity {

    MyLeaveTypesAdapter myLeaveTypesAdapter;
    ListView listView;
    ArrayList<String> keyList = new ArrayList<>();
    ArrayList<String> leaveNameList = new ArrayList();
    ArrayList<String> leaveBalList = new ArrayList();
    ArrayList<LeavesTypeListView> leavesTypeArrayList = new ArrayList();
    Toolbar toolbar;
    Button btnAddLeaveType;

    @Override
    public boolean navigateUpTo(Intent upIntent) {
        onBackPressed();
        return true;
    }
    public LeaveTypesActivity()
    {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_types);

        listView = findViewById(R.id.lv_leaves_type);
        toolbar = findViewById(R.id.toolbar_leaves_types);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnAddLeaveType = findViewById(R.id.btn_add_leave_type);
        btnAddLeaveType.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(LeaveTypesActivity.this,AddOtherLeaveTypeActivity.class);
              startActivity(intent);
              Bungee.zoom(LeaveTypesActivity.this);
              finish();
          }
      });
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("LeaveType");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                leavesTypeArrayList.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    LeaveType leaveType = snapshot.getValue(LeaveType.class);
                    keyList.add(snapshot.getKey());
                    leaveNameList.add(leaveType.getLeaveName());
                    leaveBalList.add(leaveType.getLeaveBalance());
                    myLeaveTypesAdapter.notifyDataSetChanged();
                }

                for (int i=0;i<leaveNameList.size();i++)
                {
                    LeavesTypeListView leavesTypeListView = new LeavesTypeListView(R.drawable.ic_event_busy_black_24dp,leaveNameList.get(i),leaveBalList.get(i));
                    leavesTypeArrayList.add(leavesTypeListView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        myLeaveTypesAdapter = new MyLeaveTypesAdapter(leavesTypeArrayList,LeaveTypesActivity.this,keyList);
        listView.setAdapter(myLeaveTypesAdapter);
        myLeaveTypesAdapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(LeaveTypesActivity.this);
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
                        leavesTypeArrayList.remove(position);
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                        databaseReference.child("LeaveType").child(key).removeValue();
                        myLeaveTypesAdapter.notifyDataSetChanged();
                        Intent intent = new Intent(LeaveTypesActivity.this,LeaveTypesActivity.class);
                        startActivity(intent);
                        finish();
                        Bungee.zoom(LeaveTypesActivity.this);
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
