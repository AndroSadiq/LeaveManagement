package com.example.dayoff.Admin;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dayoff.R;
import com.example.dayoff.model.LeaveType;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import spencerstudios.com.bungeelib.Bungee;

public class AddOtherLeaveTypeActivity extends AppCompatActivity {
     EditText edtLeaveName,edtLeaveBal;
     Button btnAddLeave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_other_leave_type);

        edtLeaveName = findViewById(R.id.leave_type_name);
        edtLeaveBal = findViewById(R.id.leave_type_balance);
        btnAddLeave = findViewById(R.id.btn_submit_request);
        btnAddLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtLeaveName.getText().toString().equals("")||edtLeaveBal.getText().toString().equals(""))
                {
                    Toast.makeText(AddOtherLeaveTypeActivity.this, "Enter .....", Toast.LENGTH_SHORT).show();
                }
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("LeaveType");
                String leaveKey = databaseReference.push().getKey();
                LeaveType leaveType = new LeaveType(edtLeaveName.getText().toString(),edtLeaveBal.getText().toString());
                databaseReference.child(leaveKey).setValue(leaveType);
                Intent intent = new Intent(AddOtherLeaveTypeActivity.this,LeaveTypesActivity.class);
                startActivity(intent);
                Bungee.zoom(AddOtherLeaveTypeActivity.this);
                finish();
            }
        });
    }
}
