package com.example.dayoff.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dayoff.R;
import com.example.dayoff.model.Setting;

import java.util.ArrayList;

public class SettingAdapter extends BaseAdapter {

    ArrayList<Setting> settings;
    Context context;

    public SettingAdapter(ArrayList<Setting> settings, Context context) {
        this.settings = settings;
        this.context = context;
    }

    @Override
    public int getCount() {
        return settings.size();
    }

    @Override
    public Object getItem(int position) {

        return settings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=null;

        if(view==null){

            LayoutInflater layoutInflater=LayoutInflater.from(context);

            view=layoutInflater.inflate(R.layout.setting_items,null);

        }

        TextView textView=view.findViewById(R.id.text_setting);

        textView.setText(settings.get(position).getNames());

        ImageView imageView=view.findViewById(R.id.iv_setting);
        imageView.setImageResource(settings.get(position).getImg());

        return view;
    }
}
