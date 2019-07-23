package com.example.dayoff.model;

import android.content.Context;


import com.example.dayoff.fragment.EmpFirstFragment;
import com.example.dayoff.fragment.EmpSecondFragment;
import com.example.dayoff.fragment.EmpThirdFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class EmpPager extends FragmentPagerAdapter {
      int count;
      Context context;

    public EmpPager(FragmentManager fm, int count, Context context) {
        super(fm);
        this.count = count;
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i)
        {
            case 0:
            {
                EmpFirstFragment empTab1=new EmpFirstFragment();
                return empTab1;

            }

            case 1:
            {
                EmpSecondFragment empTab2 = new EmpSecondFragment();
                return empTab2;
            }

            case 2:
            {
                EmpThirdFragment empTab3=new EmpThirdFragment();
                return empTab3;
            }
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return count;
    }
}
