package com.example.aplikasiskripsi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;

import static android.text.TextUtils.isEmpty;
import static androidx.core.content.ContextCompat.getSystemService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InputBrgFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InputBrgFragment extends Fragment {
    EditText edtTextkdbrg;
    EditText edtTextnamabrg;
    EditText edtTexthrgjual;
    EditText edtTextstokbrg;
    Button btnSimpan, btnHapus;
    private DatabaseReference myRef;
    private FirebaseDatabase fireIns;

    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_input_brg, container, false);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        edtTextkdbrg = view.findViewById(R.id.edtTextkdbrg);
        edtTextnamabrg = view.findViewById(R.id.edtTextnamabrg);
        edtTexthrgjual = view.findViewById(R.id.edtTexthrgjual);
        edtTextstokbrg = view.findViewById(R.id.edtTextstokbrg);
        btnSimpan = view.findViewById(R.id.btnSimpan);
        btnHapus = view.findViewById(R.id.btnHapus);

        FirebaseApp.initializeApp(getActivity());
        fireIns = FirebaseDatabase.getInstance();
        myRef = fireIns.getInstance().getReference();
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isEmpty(edtTextkdbrg.getText().toString().trim())
                        && !isEmpty(edtTextnamabrg.getText().toString())
                        && !isEmpty(edtTexthrgjual.getText().toString())
                        && !isEmpty(edtTextstokbrg.getText().toString()))
                    submitBarang(new BarangDB(edtTextkdbrg.getText().toString(),
                            edtTextnamabrg.getText().toString(),
                            edtTexthrgjual.getText().toString(),
                            edtTextstokbrg.getText().toString()));


                else
                    Snackbar.make(btnSimpan, "Data barang tidak boleh kosong",
                            Snackbar.LENGTH_LONG).show();

                InputMethodManager imm = (InputMethodManager) getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(
                        edtTextkdbrg.getWindowToken(), 0);


            }
        });
        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTextkdbrg.setText(" ");
                edtTextnamabrg.setText(" ");
                edtTexthrgjual.setText(" ");
                edtTextstokbrg.setText(" ");

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

    private void submitBarang(BarangDB barang) {
        /**
         * Ini adalah kode yang digunakan untuk mengirimkan data ke Firebase Realtime Database
         * dan juga kita set onSuccessListener yang berisi kode yang akan dijalankan
         * ketika data berhasil ditambahkan
         */
        myRef.child("Barang")
                .push()
                .setValue(barang)
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                edtTextkdbrg.setText("");
                edtTextnamabrg.setText("");
                edtTexthrgjual.setText("");
                edtTextstokbrg.setText("");
                Snackbar.make(btnSimpan, "Data berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
            }
        });


    }
}