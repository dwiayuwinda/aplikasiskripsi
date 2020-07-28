package com.example.aplikasiskripsi;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class LapPembFragment extends Fragment {
    TextView tgl;
    Button btnTgl;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;
    private RecyclerView recyclerView;
    private ArrayList<PembelianDB> riwayatpemb;
    private PembelianAdapter adapter;
    private FirebaseDatabase firedb;
    private DatabaseReference myRef;
    private Context context;
    private LinearLayoutManager linearLayoutManager;

    private TabLayout tabLayout;
    private ViewPager viewPager;

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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        FirebaseApp.initializeApp(getActivity());
        firedb = FirebaseDatabase.getInstance();
        myRef = firedb.getReference();
        myRef.child("Pembelian").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                riwayatpemb = new ArrayList<>();
                for (DataSnapshot mDataSnapshot : snapshot.getChildren()) {
                    PembelianDB pembelian = mDataSnapshot.getValue(PembelianDB.class);
                    pembelian.setKey(mDataSnapshot.getKey());
                    riwayatpemb.add(pembelian);
                }
                adapter = new PembelianAdapter(riwayatpemb, getActivity());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getDetails() + " "
                        + error.getMessage(), Toast.LENGTH_LONG).show();
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