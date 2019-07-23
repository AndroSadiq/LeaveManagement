package com.example.dayoff.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.dayoff.Admin.HolidaysActivity;
import com.example.dayoff.Admin.LoginActivity;
import com.example.dayoff.R;
import com.example.dayoff.Admin.SettingsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class ThirdFragment extends Fragment {
    CircleImageView circleImageView;
    TextView tvCompanyName;
    FloatingActionButton fbtnHolidays,fbtnSettings;
    Button btnSendAnnouncement;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_three,container,false);
        circleImageView = view.findViewById(R.id.circle_iv_three);
        tvCompanyName = view.findViewById(R.id.tv_company_name);
        fbtnHolidays=view.findViewById(R.id.fbtn_holidays);
        fbtnSettings=view.findViewById(R.id.fbtn_settings);
        btnSendAnnouncement=view.findViewById(R.id.btn_send_announcment);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(LoginActivity.myPref, Context.MODE_PRIVATE);
        String companyName = sharedPreferences.getString(LoginActivity.companyName,null);
        String imageUrl = sharedPreferences.getString(LoginActivity.imageUrl,null);
        if (imageUrl.equals(""))
        {

        }
        else
        {
            Picasso.get().load(imageUrl).into(circleImageView);
        }
        tvCompanyName.setText(companyName);
        fbtnHolidays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), HolidaysActivity.class);
                startActivity(intent);
            }
        });
        fbtnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });
        btnSendAnnouncement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.dialog_layout_announcement,null);
                Dialog dialog = new Dialog(getActivity());
                dialog.setCanceledOnTouchOutside(false);
                dialog.setContentView(view);
                dialog.show();
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }
        });
        return view;
    }
}
