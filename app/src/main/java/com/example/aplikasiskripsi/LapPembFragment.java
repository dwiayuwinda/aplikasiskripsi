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
 * Use the {@link LapPembFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LapPembFragment extends Fragment {
    TextView tgl;
    Button btnTgl;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LapPembFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LapPembFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LapPembFragment newInstance(String param1, String param2) {
        LapPembFragment fragment = new LapPembFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lap_pemb, container, false);
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