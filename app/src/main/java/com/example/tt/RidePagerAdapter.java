package com.example.tt;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class RidePagerAdapter extends FragmentStateAdapter {

    public RidePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return RideListFragment.newInstance("posted");
        } else {
            return RideListFragment.newInstance("booked");
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}