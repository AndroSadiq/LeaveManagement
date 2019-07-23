package com.example.dayoff.Admin;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dayoff.R;
import com.example.dayoff.model.HolidayName;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import spencerstudios.com.bungeelib.Bungee;

public class AddHolidayActivity extends AppCompatActivity {
      EditText edtHolidayName,edtStartDate,edtEndDate;
      Button btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_holiday);

        btnAdd = findViewById(R.id.btn_add_holiday);
        edtHolidayName = findViewById(R.id.edt_add_holiday_name);
        edtStartDate = findViewById(R.id.edt_star_date);
        edtEndDate =findViewById(R.id.edt_end_date);
        edtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater=getLayoutInflater();
                View view=inflater.inflate(R.layout.dialog_lay_date,null);
                final Dialog dialog=new Dialog(AddHolidayActivity.this);
                dialog.setContentView(view);
               final DatePicker datePicker=view.findViewById(R.id.date_picker);
               Button btnSubmit=view.findViewById(R.id.btn_submit);
               Button btnCancel=view.findViewById(R.id.btn_cancel);
                dialog.show();
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String date=datePicker.getDayOfMonth()+"/"+(datePicker.getMonth()+1)+"/"+datePicker.getYear();
                        try {
                            Date t=new SimpleDateFormat("dd/MM/yyyy").parse(date);
                            edtStartDate.setText(new SimpleDateFormat("EEEE, MMMM d, yyyy").format(t));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        edtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater=getLayoutInflater();
                View view=inflater.inflate(R.layout.dialog_lay_date,null);
                final Dialog dialog=new Dialog(AddHolidayActivity.this);
                dialog.setContentView(view);
                final DatePicker datePicker=view.findViewById(R.id.date_picker);
                Button btnSubmit=view.findViewById(R.id.btn_submit);
                Button btnCancel=view.findViewById(R.id.btn_cancel);
                dialog.show();
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String date=datePicker.getDayOfMonth()+"/"+(datePicker.getMonth()+1)+"/"+datePicker.getYear();
                        try {
                            Date t=new SimpleDateFormat("dd/MM/yyyy").parse(date);
                            edtEndDate.setText(new SimpleDateFormat("EEEE, MMMM d, yyyy").format(t));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtHolidayName.getText().toString().equals("") || edtStartDate.getText().toString().equals("")|| edtEndDate.getText().toString().equals(""))
                {
                    Toast.makeText(AddHolidayActivity.this, "Enter .....", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("OfficialHolidays");
                    String HolidayId = databaseReference.push().getKey();
                    HolidayName holidayName = new HolidayName(edtHolidayName.getText().toString(),edtStartDate.getText().toString(),edtEndDate.getText().toString());
                    databaseReference.child(HolidayId).setValue(holidayName);
                    Intent intent = new Intent(AddHolidayActivity.this,HolidaysActivity.class);
                    startActivity(intent);
                    finish();
                    Bungee.zoom(AddHolidayActivity.this);
                }
            }
        });
            }
    }

