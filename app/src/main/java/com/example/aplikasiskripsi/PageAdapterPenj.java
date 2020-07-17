package com.example.aplikasiskripsi;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapterPenj extends FragmentPagerAdapter {
    private int numOfTabs;

    public PageAdapterPenj (FragmentManager fm, int numOfTabs){
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new TransPenjFragment();
            case 1:
                return new LapPenjFragment();
            default:
                return null;
        }
    }

    public int getCount() {
        return numOfTabs;
    }
}
