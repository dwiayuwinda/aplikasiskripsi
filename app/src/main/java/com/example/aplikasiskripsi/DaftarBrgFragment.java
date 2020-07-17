package com.example.aplikasiskripsi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.os.TraceCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DaftarBrgFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DaftarBrgFragment extends Fragment implements ItemAdapter.FirebaseDataListener {
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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DaftarBrgFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static DaftarBrgFragment newInstance(String param1, String param2) {
        DaftarBrgFragment fragment = new DaftarBrgFragment();
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
        myRef.child("data barang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                daftarbarang = new ArrayList<>();
                for (DataSnapshot mDataSnapshot : snapshot.getChildren()) {
                    BarangDB barang = mDataSnapshot.getValue(BarangDB.class);
                    barang.setKey(mDataSnapshot.getKey());
                    daftarbarang.add(barang);
                }
                adapter = new ItemAdapter(context, daftarbarang);
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void dialogUpdateBarang(final BarangDB barang) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Edit Data");
        View view = getLayoutInflater().inflate(R.layout.layout_editbarang, null);

        kode_brg = view.findViewById(R.id.kode_brg);
        nama_brg = view.findViewById(R.id.nama_brg);
        hrg_jual = view.findViewById(R.id.hrg_jual);
        stok_brg = view.findViewById(R.id.stok_brg);

        kode_brg.setText(barang.getKode());
        nama_brg.setText(barang.getNama());
        hrg_jual.setText(barang.getHarga());
        stok_brg.setText(barang.getStok());
        builder.setView(view);

        if (barang != null) {
            builder.setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    barang.setKode(kode_brg.getText().toString());
                    barang.setNama(nama_brg.getText().toString());
                    barang.setHarga(hrg_jual.getText().toString());
                    barang.setStok(stok_brg.getText().toString());
                    updateDataBarang(barang);
                }
            });
        }
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
        myRef.child("data barang").child(barang.getKey())
                .setValue(barang).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getActivity(), "Data berhasil di update", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void hapusDataBarang(BarangDB barang) {
        if (myRef != null) {
            myRef.child("data barang").child(barang.getKey()).setValue(barang).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getActivity(), "Data berhasil di hapus", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void OnDataClick(final BarangDB barang, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pilih Aksi");

        builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogUpdateBarang(barang);
            }
        });

        builder.setPositiveButton("HAPUS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                hapusDataBarang(barang);
            }
        });

        builder.setPositiveButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }
}