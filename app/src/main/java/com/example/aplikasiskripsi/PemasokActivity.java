package com.example.aplikasiskripsi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class PemasokActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    PageAdapterSupp pageAdapterSupp;
    TabItem tabInputSupp, tabDaftarSupp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemasok);
        tabLayout = findViewById(R.id.tabLayout);
        tabInputSupp = findViewById(R.id.tabInputSupp);
        tabDaftarSupp = findViewById(R.id.tabDaftarSupp);
        viewPager = findViewById(R.id.viewPager);

        pageAdapterSupp = new PageAdapterSupp (getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapterSupp);

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