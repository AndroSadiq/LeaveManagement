package com.example.dayoff.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;


public class PagerImagesAdapter extends PagerAdapter
{

    int[] layouts;
    Context context;

    public PagerImagesAdapter(Context context,int[] layouts) {
        this.context = context;
        this.layouts = layouts;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);

        View view=layoutInflater.inflate(layouts[position],null);

        container.addView(view);
        return view;

    }

    @Override
    public int getCount() {
        return layouts.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                    View view=(View) object;

                    container.removeView(view);
    }
}