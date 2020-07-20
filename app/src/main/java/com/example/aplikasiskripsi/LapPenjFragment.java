package com.example.aplikasiskripsi;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LapPenjFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LapPenjFragment extends Fragment {
    TextView tgl;
    Button btnTgl;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lap_penj, container, false);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        tgl = view.findViewById(R.id.tgl);
        btnTgl = view.findViewById(R.id.btnTgl);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        btnTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });
        return view;
    }
        private void showDateDialog(){

            Calendar newcal = Calendar.getInstance();

            datePickerDialog = new DatePickerDialog( getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int tahun, int bulan, int tanggal) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(tahun, bulan, tanggal);
                    tgl.setText(dateFormatter.format(newDate.getTime()));
                }
            },newcal.get(Calendar.YEAR), newcal.get(Calendar.MONTH), newcal.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();

        }
}