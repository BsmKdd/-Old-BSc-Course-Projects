package com.example.mygym;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class workoutsFragment extends Fragment
{
    private ViewPager viewPager;
    ViewPagerAdapter pagerAdapter;
    BottomNavigationView bottomNavigationView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.workouts_fragment, container, false);

        viewPager = rootView.findViewById(R.id.viewpager);
        bottomNavigationView = rootView.findViewById(R.id.bottom_navigation);
        pagerAdapter = new ViewPagerAdapter(getFragmentManager());
        viewPager.setOffscreenPageLimit(3);

        final BottomNavigationView bottomNavigationView = rootView.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.nav_currentWorkout:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.nav_assignedWorkout:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.nav_previousWorkouts:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.nav_customizedWorkout:
                        viewPager.setCurrentItem(3);
                        break;
                }

                return true;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.nav_currentWorkout).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.nav_assignedWorkout).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.nav_previousWorkouts).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.nav_customizedWorkout).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        viewPager.setAdapter(pagerAdapter);
        return rootView;


    }

}


