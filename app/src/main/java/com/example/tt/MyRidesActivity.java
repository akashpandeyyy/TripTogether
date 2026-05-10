package com.example.tt;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MyRidesActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rides);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        viewPager.setAdapter(new MyPagerAdapter(this));

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {

                    if(position == 0){
                        tab.setText("Posted");
                    } else {
                        tab.setText("Booked");
                    }

                }).attach();
    }

    // VIEWPAGER ADAPTER

    static class MyPagerAdapter extends FragmentStateAdapter {

        public MyPagerAdapter(AppCompatActivity activity) {
            super(activity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {

            if(position == 0){
                return RideListFragment.newInstance("posted");
            }

            return RideListFragment.newInstance("booked");
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}