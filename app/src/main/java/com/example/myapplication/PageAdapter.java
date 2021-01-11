package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myapplication.Dispatch_fragment;
import com.example.myapplication.Pickup_fragment;

import java.lang.reflect.Array;
import java.util.Arrays;

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
            case 0 :
                Bundle bundle = new Bundle();          // user to pass data from activity/class to Fragment
                Login_activity login_activity= new Login_activity();
                String[] challanlist =  login_activity.getchallanlist();
                bundle.putStringArray("challanlist", challanlist);
                Dispatch_fragment fragobj = new Dispatch_fragment();
                fragobj.setArguments(bundle);
                return fragobj;
            case 1 : return new Pickup_fragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}