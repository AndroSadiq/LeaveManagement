package com.example.dayoff.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dayoff.R;
import com.example.dayoff.model.HolidayListView;

import java.util.ArrayList;

public class MyHolidayAdapter extends BaseAdapter {
    ArrayList<HolidayListView> arrayList;
    Context context;
    ArrayList<String> keyList;

    public MyHolidayAdapter(ArrayList<HolidayListView> arrayList, Context context, ArrayList<String> keyList) {
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
            view=layoutInflater.inflate(R.layout.holiday_list_lay,null);
        }
        ImageView imageView = view.findViewById(R.id.iv_holiday);
        TextView tvName = view.findViewById(R.id.tv_holiday_name);
        TextView tvStartDate = view.findViewById(R.id.tv_start_date);
        TextView tvEndDate = view.findViewById(R.id.tv_end_date);
        imageView.setImageResource(arrayList.get(position).getImg());
        tvName.setText(arrayList.get(position).getHolidayName1());
        tvStartDate.setText("Start:"+arrayList.get(position).getStartDate1());
        tvEndDate.setText("End:"+arrayList.get(position).getEndDate1());
        return view;
    }
}
