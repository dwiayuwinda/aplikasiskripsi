package com.example.aplikasiskripsi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class BarangActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    PageAdapterBarang pageAdapterBarang;
    TabItem tabInputBrg, tabDaftarBrg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang);
        tabLayout = findViewById(R.id.tabLayout);
        tabInputBrg = findViewById(R.id.tabTransPemb);
        tabDaftarBrg = findViewById(R.id.tabDaftarBrg);
        viewPager = findViewById(R.id.viewPager);

        pageAdapterBarang = new PageAdapterBarang (getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapterBarang);

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