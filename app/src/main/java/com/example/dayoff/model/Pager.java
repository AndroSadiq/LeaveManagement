package com.example.dayoff.model;

import com.example.dayoff.fragment.SecondFragment;
import com.example.dayoff.fragment.ThirdFragment;
import com.example.dayoff.fragment.FirstFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class Pager extends FragmentPagerAdapter {

    private int count;

    public Pager(FragmentManager fm, int count) {
        super(fm);
        this.count = count;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
            {
              return new FirstFragment();
            }

            case 1:
            {
              return new SecondFragment();
            }

            case 2:
            {
              return new ThirdFragment();
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
