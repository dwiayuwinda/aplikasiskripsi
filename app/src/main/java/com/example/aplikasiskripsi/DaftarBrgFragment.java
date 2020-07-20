package com.example.aplikasiskripsi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DaftarBrgFragment extends Fragment {
    EditText edtTextSearch;
    private ArrayList<BarangDB> daftarbarang;
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private DatabaseReference myRef;
    private FirebaseDatabase fireIns;
    private EditText kode_brg;
    private EditText nama_brg;
    private EditText hrg_jual;
    private EditText stok_brg;
    private Context context;
    private LinearLayoutManager linearLayoutManager;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daftar_brg, container, false);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        FirebaseApp.initializeApp(getActivity());
        fireIns = FirebaseDatabase.getInstance();
        myRef = fireIns.getReference();
        myRef.child("Barang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                daftarbarang = new ArrayList<>();
                for (DataSnapshot mDataSnapshot : snapshot.getChildren()) {
                    BarangDB barang = mDataSnapshot.getValue(BarangDB.class);
                    barang.setKode(mDataSnapshot.getKey());
                    daftarbarang.add(barang);
                }
                adapter = new ItemAdapter(daftarbarang, getActivity());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getActivity(), error.getDetails() + " "
                        + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        edtTextSearch = view.findViewById(R.id.edtTextSearch);
        edtTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        return view;
    }

    private void filter(String text){
        ArrayList<BarangDB> filteredList = new ArrayList<>();

        for (BarangDB item : daftarbarang){
            if (item.getNama().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }
}