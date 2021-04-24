package com.example.mygym;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new currentWorkoutFragment();
            case 1:
                return new assignedWorkoutFragment();
            case 2:
                return new previousWorkoutFragment();
            case 3:
                return new customizeWorkoutFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
