package com.example.aplikasiskripsi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
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

public class DaftarBrgFragment extends Fragment implements ItemAdapter.ItemClickListener{
    EditText edtTextSearch;
    TextView stok_brg;
    ArrayList<BarangDB> daftarbarang;
    RecyclerView recyclerView;
    ItemAdapter adapter;
    DatabaseReference myRef;
    FirebaseDatabase fireIns;
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
        stok_brg = view.findViewById(R.id.stok_brg);
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
                    barang.setNama(mDataSnapshot.getKey());
                    daftarbarang.add(barang);
                }
                adapter = new ItemAdapter(daftarbarang, getActivity(), DaftarBrgFragment.this);
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

    @Override
    public void onDataClick(final BarangDB barang) {
        String[] options = {"Edit","Hapus"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pilih Aksi").setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i==0){
                    dialogUpdateBarang(barang);
                }else if (i==1){
                    hapusDataBarang(barang);
                }
            }
        }).show();
    }

    private void hapusDataBarang(BarangDB barang) {
        myRef.child("Barang")
                .child(barang.getNama())
                .setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(requireContext(), "Data dihapus", Toast.LENGTH_LONG).show();
                }
                });
    }

    private void dialogUpdateBarang(final BarangDB barang) {
        final EditText kode_brg,nama_brg,hrg_jual,stok_brg;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Edit Data");
        View view;
        view = getLayoutInflater().inflate(R.layout.layout_editbarang, null);

        kode_brg = view.findViewById(R.id.kode_brg);
        nama_brg = view.findViewById(R.id.nama_brg);
        hrg_jual = view.findViewById(R.id.hrg_jual);
        stok_brg = view.findViewById(R.id.stok_brg);

        kode_brg.setText(barang.getKode());
        nama_brg.setText(barang.getNama());
        hrg_jual.setText(barang.getHarga());
        stok_brg.setText(barang.getStok());
        builder.setView(view);

        builder.setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    barang.setKode(kode_brg.getText().toString().trim());
                    barang.setNama(nama_brg.getText().toString().trim());
                    barang.setHarga(hrg_jual.getText().toString().trim());
                    barang.setStok(stok_brg.getText().toString().trim());
                    updateDataBarang(barang);
                }
        });

        builder.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }

    private void updateDataBarang(BarangDB barang) {
        myRef.child("Barang")
                .child(barang.getNama())
                .setValue(barang).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getActivity(), "Data berhasil di update", Toast.LENGTH_LONG).show();
            }
        });
    }
}