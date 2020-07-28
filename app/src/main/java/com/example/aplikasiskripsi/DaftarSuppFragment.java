package com.example.aplikasiskripsi;

import android.app.AlertDialog;
import android.app.Dialog;
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

public class DaftarSuppFragment extends Fragment implements SupplierAdapter.ItemClickListener{
    EditText edtTextSearch;
    private ArrayList<SupplierDB> daftarsupplier;
    private RecyclerView recyclerView;
    private SupplierAdapter adapter;
    private DatabaseReference myRef;
    private FirebaseDatabase fireIns;
    private EditText kdsupp;
    private EditText namasupp;
    private EditText alamat;
    private EditText telepon;
    private EditText ket;
    private Context context;
    private LinearLayoutManager linearLayoutManager;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daftar_supp, container, false);
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
        myRef.child("Pemasok").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                daftarsupplier = new ArrayList<>();
                for (DataSnapshot mDataSnapshot : snapshot.getChildren()) {
                    SupplierDB supplier = mDataSnapshot.getValue(SupplierDB.class);
                    supplier.setKode(mDataSnapshot.getKey());
                    daftarsupplier.add(supplier);
                }
                adapter = new SupplierAdapter(daftarsupplier, getActivity(), DaftarSuppFragment.this);
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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });
        return view;
    }

    private void filter(String text){
        ArrayList<SupplierDB> filteredList = new ArrayList<>();

        for (SupplierDB item : daftarsupplier){
            if (item.getNama().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }

    @Override
    public void onDataClick(final SupplierDB supplier) {
        String[] options = {"Edit","Hapus"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pilih Aksi").setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i==0){
                    dialogUpdateSupplier(supplier);
                }else if (i==1){
                    hapusDataSupplier(supplier);
                }
            }
        }).show();
    }

    private void dialogUpdateSupplier(final SupplierDB supplier) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Edit Data");
        View view = getLayoutInflater().inflate(R.layout.layout_editsupp, null);

        kdsupp = view.findViewById(R.id.kdsupp);
        namasupp = view.findViewById(R.id.namasupp);
        alamat = view.findViewById(R.id.alamat);
        telepon = view.findViewById(R.id.telepon);
        ket = view.findViewById(R.id.ket);

        kdsupp.setText(supplier.getKode());
        namasupp.setText(supplier.getNama());
        alamat.setText(supplier.getAlamat());
        telepon.setText(supplier.getTelepon());
        ket.setText(supplier.getKet());
        builder.setView(view);

        builder.setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    supplier.setKode(kdsupp.getText().toString());
                    supplier.setNama(namasupp.getText().toString());
                    supplier.setAlamat(alamat.getText().toString());
                    supplier.setTelepon(telepon.getText().toString());
                    supplier.setKet(ket.getText().toString());
                    updateDataSupplier(supplier);
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

    private void updateDataSupplier(SupplierDB supplier) {
        myRef.child("Pemasok").child(supplier.getKode())
                .setValue(supplier).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getActivity(), "Data berhasil di update", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void hapusDataSupplier(SupplierDB supplier) {
        myRef.child("Pemasok").child(supplier.getKode()).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(requireContext(), "Data berhasil di hapus", Toast.LENGTH_LONG).show();
                }
        });
    }


}