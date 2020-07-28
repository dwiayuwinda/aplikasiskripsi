package com.example.aplikasiskripsi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.text.TextUtils.isEmpty;

public class TransPenjFragment extends Fragment{
    TextView autotgl;
    TextView tvkdbrg;
    TextView tvhrgbrg;
    TextView totalpj;
    EditText edtTextnamabrg;
    EditText edtTextjmlbeli;
    Button btnLihat, btnCek, btnProses, btnHapus;
    FirebaseDatabase firedb;
    DatabaseReference myRef;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trans_penj, container, false);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        autotgl = view.findViewById(R.id.autotgl);
        edtTextnamabrg = view.findViewById(R.id.edtTextnamabrg);
        tvhrgbrg = view.findViewById(R.id.tvhrgbrg);
        totalpj = view.findViewById(R.id.total);
        tvkdbrg = view.findViewById(R.id.tvkdbrg);
        edtTextjmlbeli = view.findViewById(R.id.edtTextjmlbeli);
        btnLihat = view.findViewById(R.id.btnLihat);
        btnCek = view.findViewById(R.id.btnCek);
        btnProses = view.findViewById(R.id.btnProses);
        btnHapus = view.findViewById(R.id.btnHapus);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String date = df.format(cal.getTime());
        autotgl.setText(date);

        FirebaseApp.initializeApp(getActivity());
        firedb = FirebaseDatabase.getInstance();
        myRef = firedb.getReference();

        btnLihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BarangActivity.class);
                startActivity(intent);
            }
        });

        btnCek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String namabrg = edtTextnamabrg.getText().toString().trim();
                myRef = firedb.getInstance().getReference();
                myRef.child("Barang").child(namabrg).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        BarangDB barang = snapshot.getValue(BarangDB.class);
                        tvkdbrg.setText(barang.getKode());
                        tvhrgbrg.setText(barang.getHarga());


                        String jmlbeli = edtTextjmlbeli.getText().toString().trim();
                        String hrgbrg = tvhrgbrg.getText().toString().trim();


                        double jb =Double.parseDouble(jmlbeli);
                        double hb = Double.parseDouble(hrgbrg);

                        double total = (jb*hb);
                        totalpj.setText(" "+total);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(), error.getDetails() + " "
                                + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        btnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEmpty(autotgl.getText().toString())
                        && !isEmpty(tvkdbrg.getText().toString())
                        && !isEmpty(tvhrgbrg.getText().toString())
                        && !isEmpty(totalpj.getText().toString())
                        && !isEmpty(edtTextnamabrg.getText().toString())
                        && !isEmpty(edtTextjmlbeli.getText().toString()))
                    submitPenj(new PenjualanDB(autotgl.getText().toString(),
                            tvkdbrg.getText().toString(),
                            tvhrgbrg.getText().toString(),
                            totalpj.getText().toString(),
                            edtTextnamabrg.getText().toString(),
                            edtTextjmlbeli.getText().toString()));



                else
                    Snackbar.make(btnProses, "Data penjualan tidak boleh kosong",
                            Snackbar.LENGTH_LONG).show();

                InputMethodManager imm = (InputMethodManager) getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(
                        autotgl.getWindowToken(), 0);
            }
        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvkdbrg.setText(" ");
                tvhrgbrg.setText(" ");
                totalpj.setText(" ");
                edtTextnamabrg.setText(" ");
                edtTextjmlbeli.setText(" ");

                Snackbar.make(btnHapus, "Data sudah dihapus",
                        Snackbar.LENGTH_LONG).show();
            }
        });
        return view;
    }

    private boolean isEmpty(String s) {
        // Cek apakah ada fields yang kosong, sebelum disubmit
        return TextUtils.isEmpty(s);
    }

    private void submitPenj(PenjualanDB penjualan) {
        /**
         * Ini adalah kode yang digunakan untuk mengirimkan data ke Firebase Realtime Database
         * dan juga kita set onSuccessListener yang berisi kode yang akan dijalankan
         * ketika data berhasil ditambahkan
         */
        String keyId = myRef.push().getKey();
        myRef.child("Penjualan")
                .child(keyId)
                .setValue(penjualan)
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        tvkdbrg.setText(" ");
                        tvhrgbrg.setText(" ");
                        totalpj.setText(" ");
                        edtTextnamabrg.setText(" ");
                        edtTextjmlbeli.setText(" ");
                        Snackbar.make(btnProses, "Transaksi berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
                    }
                });
    }
}