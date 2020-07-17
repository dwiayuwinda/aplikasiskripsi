package com.example.aplikasiskripsi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class PembelianActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    PageAdapterPemb pageAdapterPemb;
    TabItem tabTransPemb, tabLapPemb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembelian);
        tabLayout = findViewById(R.id.tabLayout);
        tabTransPemb = findViewById(R.id.tabTransPemb);
        tabLapPemb = findViewById(R.id.tabLapPemb);
        viewPager = findViewById(R.id.viewPager);

        pageAdapterPemb = new PageAdapterPemb (getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapterPemb);

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