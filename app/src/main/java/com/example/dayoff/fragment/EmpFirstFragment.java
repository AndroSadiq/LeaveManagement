package com.example.dayoff.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dayoff.Admin.AddHolidayActivity;
import com.example.dayoff.R;
import com.example.dayoff.model.RequestHoliday;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmpFirstFragment extends Fragment {
    EditText edtFromDate,edtToDate,edtReason;
    Button btnRequest;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.emp_fragment_first,null);
        edtFromDate = view.findViewById(R.id.edt_from);
        edtToDate = view.findViewById(R.id.edt_to);
        edtReason = view.findViewById(R.id.edt_reason);
        btnRequest = view.findViewById(R.id.btn_submit_request);

        edtFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater=getLayoutInflater();
                View view1=inflater.inflate(R.layout.dialog_lay_date,null);
                final Dialog dialog=new Dialog(getActivity());
                dialog.setContentView(view1);
                final DatePicker datePicker=view1.findViewById(R.id.date_picker);
                Button btnSubmit=view1.findViewById(R.id.btn_submit);
                Button btnCancel=view1.findViewById(R.id.btn_cancel);
                dialog.show();
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String date=datePicker.getDayOfMonth()+"/"+(datePicker.getMonth()+1)+"/"+datePicker.getYear();
                        try {
                            Date t=new SimpleDateFormat("dd/MM/yyyy").parse(date);
                            edtFromDate.setText(new SimpleDateFormat("dd MMM yyyy").format(t));
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
        edtToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater=getLayoutInflater();
                View view1=inflater.inflate(R.layout.dialog_lay_date,null);
                final Dialog dialog=new Dialog(getActivity());
                dialog.setContentView(view1);
                final DatePicker datePicker=view1.findViewById(R.id.date_picker);
                Button btnSubmit=view1.findViewById(R.id.btn_submit);
                Button btnCancel=view1.findViewById(R.id.btn_cancel);
                dialog.show();
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String date=datePicker.getDayOfMonth()+"/"+(datePicker.getMonth()+1)+"/"+datePicker.getYear();
                        try {
                            Date t=new SimpleDateFormat("dd/MM/yyyy").parse(date);
                            edtToDate.setText(new SimpleDateFormat("dd MMM yyyy").format(t));
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
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtFromDate.getText().toString().equals("")||edtToDate.getText().toString().equals("")||edtReason.getText().toString().equals(""))
                {
                    Toast.makeText(v.getContext(), "Enter .....", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("RequestHoliday");
                    String RequestId = databaseReference.push().getKey();
                    RequestHoliday requestHoliday = new RequestHoliday(edtFromDate.getText().toString(),edtToDate.getText().toString(),edtReason.getText().toString(),"pending");
                    databaseReference.child(RequestId).setValue(requestHoliday);
                    Toast.makeText(v.getContext(), "Request Sent", Toast.LENGTH_SHORT).show();
                    edtFromDate.setText("");
                    edtToDate.setText("");
                    edtReason.setText("");

                }
            }
        });
        return view;
    }
}
