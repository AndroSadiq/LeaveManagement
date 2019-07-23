package com.example.dayoff.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dayoff.Admin.LoginActivity;
import com.example.dayoff.employeeViewActivity.EmpHolidaysActivity;
import com.example.dayoff.employeeViewActivity.EmpLoginActivity;
import com.example.dayoff.employeeViewActivity.EmpSettingActivity;
import com.example.dayoff.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmpThirdFragment extends Fragment {
    CircleImageView circleImageView;
    TextView tvName;
    FloatingActionButton fbtnEmpHolidays,fbtnEmpSetting;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.emp_fragment_third,null);
       circleImageView = view.findViewById(R.id.circle_iv_three_emp);
       tvName = view.findViewById(R.id.tv_name);
       fbtnEmpHolidays=view.findViewById(R.id.fbtn_emp_holidays);
       fbtnEmpSetting=view.findViewById(R.id.fbtn_emp_settings);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(EmpLoginActivity.myEmpPref, Context.MODE_PRIVATE);
        String name = sharedPreferences.getString(EmpLoginActivity.Empname,null);
        String imageUrl = sharedPreferences.getString(EmpLoginActivity.EmpimageUrl,null);
        if (imageUrl.equals(""))
        {

        }
        else
        {
            Picasso.get().load(imageUrl).into(circleImageView);
        }
        tvName.setText(name);
        fbtnEmpHolidays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(), EmpHolidaysActivity.class);
                startActivity(intent);

            }
        });

        fbtnEmpSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(), EmpSettingActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
