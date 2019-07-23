package com.example.dayoff.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dayoff.R;
import com.example.dayoff.model.EmpSetting;

import java.util.ArrayList;

public class EmpAdapter extends BaseAdapter {

    ArrayList<EmpSetting> arrayList;

    Context context;

    public EmpAdapter(ArrayList<EmpSetting> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
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
    public View getView(int position, View view, ViewGroup parent) {

        view=null;
        if (view==null)
        {
            LayoutInflater layoutInflater=LayoutInflater.from(context);
            view=layoutInflater.inflate(R.layout.emp_setting_list_item,null);
        }

        ImageView imageView=view.findViewById(R.id.iv_emp_setting);
        TextView textView=view.findViewById(R.id.tv_emp_setting);

        imageView.setImageResource(arrayList.get(position).getImg());
        textView.setText(arrayList.get(position).getName());

        return view;
    }
}
