package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myapplication.Dispatch_fragment;
import com.example.myapplication.Pickup_fragment;

public class PageAdapter extends FragmentPagerAdapter
{
    int tabcount;

    public PageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount=behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        switch (position)   // based on postion fragment loaded
        {
            case 0 : return new Dispatch_fragment();
            case 1 : return new Pickup_fragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}