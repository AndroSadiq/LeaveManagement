package com.example.dayoff.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dayoff.R;
import com.example.dayoff.model.LeavesTypeListView;

import java.util.ArrayList;

public class MyLeaveTypesAdapter extends BaseAdapter {
    ArrayList<LeavesTypeListView> arrayList;
    Context context;
    ArrayList<String> keyList;

    public MyLeaveTypesAdapter(ArrayList<LeavesTypeListView> arrayList, Context context, ArrayList<String> keyList) {
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
            view=layoutInflater.inflate(R.layout.leaves_type_list_lay,null);
        }

        ImageView imageView = view.findViewById(R.id.iv_leave_type);
        TextView tvLeaveName = view.findViewById(R.id.tv_leave_type_name);
        TextView tvLeaveBal = view.findViewById(R.id.tv_leave_balance);
        imageView.setImageResource(arrayList.get(position).getImg());
        tvLeaveName.setText(arrayList.get(position).getLeaveNAME());
        tvLeaveBal.setText("Total Leave:"+arrayList.get(position).getLeaveBal()+" days");
        return view;
    }
}
