package com.example.dayoff.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dayoff.R;
import com.example.dayoff.model.SecondFrag;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MySeconFragAdapter extends BaseAdapter {
    ArrayList<SecondFrag> arrayList;
    Context context;
    ArrayList<String> keyList;

    public MySeconFragAdapter(ArrayList<SecondFrag> arrayList, Context context, ArrayList<String> keyList) {
        this.arrayList = arrayList;
        this.context = context;
        this.keyList = keyList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=null;
        if (view==null)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view=layoutInflater.inflate(R.layout.secon_frag_list_lay,null);
        }
        ImageView imageView = view.findViewById(R.id.iv_second_frag);
        TextView tvReason = view.findViewById(R.id.tv_second_frag_name);
        TextView tvStartDate = view.findViewById(R.id.tv_second_frag_start_date);
        TextView tvEndDate = view.findViewById(R.id.tv_second_frag_end_date);
        FloatingActionButton fbtnOk = view.findViewById(R.id.fbtn_ok);
        FloatingActionButton fbtnClose = view.findViewById(R.id.fbtn_close);
        imageView.setImageResource(arrayList.get(position).getImg());
        tvReason.setText(arrayList.get(position).getReason());
        tvStartDate.setText("From: "+arrayList.get(position).getStartDate());
        tvEndDate.setText("To: "+arrayList.get(position).getEndDate());
        fbtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        fbtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }
}
