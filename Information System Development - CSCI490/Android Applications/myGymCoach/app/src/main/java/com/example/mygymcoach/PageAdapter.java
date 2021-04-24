package com.example.mygymcoach;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter
{
    private int numOfTabs;

    PageAdapter(FragmentManager fm, int numOfTabs)
    {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0: return new assignFragment();
            case 1: return new reportFragment();
            default: return null;
        }
    }

    @Override
    public int getCount()
    {
        return numOfTabs;
    }
}
