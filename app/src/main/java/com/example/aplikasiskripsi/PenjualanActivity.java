package com.example.aplikasiskripsi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class PenjualanActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    PageAdapterPenj pageAdapterPenj;
    TabItem tabTransPenj, tabLapPenj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan);
        tabLayout = findViewById(R.id.tabLayout);
        tabTransPenj = findViewById(R.id.tabTransPenj);
        tabLapPenj = findViewById(R.id.tabLapPenj);
        viewPager = findViewById(R.id.viewPager);

        pageAdapterPenj = new PageAdapterPenj (getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapterPenj);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}