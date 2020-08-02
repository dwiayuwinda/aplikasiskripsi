package com.example.aplikasiskripsi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
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
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.text.TextUtils.isEmpty;


public class TransPembFragment extends Fragment  {
    TextView autotgl;
    TextView tvkdsupp;
    TextView tvkdbrg;
    TextView totalpb;
    EditText edtTextnamasupp;
    EditText edtTextnamabrg;
    EditText edtTextkuantitas;
    EditText edtTexthrgbeli;
    Button btnLihatsupp, btnLihatbrg, btnCek, btnProses, btnHapus;
    FirebaseDatabase firedb;
    DatabaseReference myRef;
    DatabaseReference myRef_brg;
    DatabaseReference myRef_supp;
    IFirebaseLoadDone iFirebaseLoadDone;
    ArrayList<SupplierDB> daftarsupp;


    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trans_pemb, container, false);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        autotgl = view.findViewById(R.id.autotgl);
        tvkdsupp = view.findViewById(R.id.tvkdsupp);
        tvkdbrg = view.findViewById(R.id.tvkdbrg);
        totalpb = view.findViewById(R.id.totalpb);
        edtTextnamasupp = view.findViewById(R.id.edtTextnamasupp);
        edtTextnamabrg = view.findViewById(R.id.edtTextnamabrg);
        edtTextkuantitas = view.findViewById(R.id.edtTextkuantitas);
        edtTexthrgbeli = view.findViewById(R.id.edtTexthrgbeli);
        btnLihatbrg = view.findViewById(R.id.btnLihatbrg);
        btnLihatsupp = view.findViewById(R.id.btnLihatsupp);
        btnCek = view.findViewById(R.id.btnCek);
        btnProses = view.findViewById(R.id.btnProses);
        btnHapus = view.findViewById(R.id.btnHapus);

        //iFirebaseLoadDone = this;

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String date = df.format(cal.getTime());
        autotgl.setText(date);

        FirebaseApp.initializeApp(getActivity());
        firedb = FirebaseDatabase.getInstance();
        myRef = firedb.getReference();

        btnLihatbrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BarangActivity.class);
                startActivity(intent);
            }
        });

        btnLihatsupp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PemasokActivity.class);
                startActivity(intent);
            }
        });

        btnCek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String namabrg = edtTextnamabrg.getText().toString().trim();
                myRef_brg = firedb.getReference();
                myRef_brg.child("Barang").child(namabrg).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        BarangDB barang = snapshot.getValue(BarangDB.class);
                        tvkdbrg.setText(barang.getKode());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(), error.getDetails() + " "
                                + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                final String namasupp = edtTextnamasupp.getText().toString().trim();
                myRef_supp = firedb.getReference();
                myRef_supp.child("Pemasok").child(namasupp).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        SupplierDB supplier = snapshot.getValue(SupplierDB.class);
                        tvkdsupp.setText(supplier.getKode());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(), error.getDetails() + " "
                                + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                String kuantitas = edtTextkuantitas.getText().toString().trim();
                String hargabeli = edtTexthrgbeli.getText().toString().trim();

                double k  = Double.parseDouble(kuantitas);
                double hb = Double.parseDouble(hargabeli);
                double total = (k*hb);

                totalpb.setText(" "+total);
            }
        });

        btnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEmpty(autotgl.getText().toString())
                        && !isEmpty(tvkdsupp.getText().toString())
                        && !isEmpty(tvkdbrg.getText().toString())
                        && !isEmpty(totalpb.getText().toString())
                        && !isEmpty(edtTextnamasupp.getText().toString())
                        && !isEmpty(edtTextnamabrg.getText().toString())
                        && !isEmpty(edtTextkuantitas.getText().toString())
                        && !isEmpty(edtTexthrgbeli.getText().toString()))
                    submitPemb(new PembelianDB(autotgl.getText().toString(),
                            tvkdsupp.getText().toString(),
                            edtTextnamasupp.getText().toString(),
                            tvkdbrg.getText().toString(),
                            edtTextnamabrg.getText().toString(),
                            edtTextkuantitas.getText().toString(),
                            edtTexthrgbeli.getText().toString(),
                            totalpb.getText().toString()));

                else
                    Snackbar.make(btnProses, "Data pembelian tidak boleh kosong",
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
                tvkdsupp.setText(" ");
                tvkdbrg.setText(" ");
                totalpb.setText(" ");
                edtTextnamabrg.setText(" ");
                edtTextnamasupp.setText(" ");
                edtTextkuantitas.setText(" ");
                edtTexthrgbeli.setText(" ");

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

    private void submitPemb(PembelianDB pembelian) {
        String keypb = myRef.push().getKey();
        myRef.child("Pembelian")
                .child(keypb)
                .setValue(pembelian)
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        tvkdsupp.setText(" ");
                        tvkdbrg.setText(" ");
                        totalpb.setText(" ");
                        edtTextnamabrg.setText(" ");
                        edtTextnamasupp.setText(" ");
                        edtTextkuantitas.setText(" ");
                        edtTexthrgbeli.setText(" ");
                        Snackbar.make(btnProses, "Transaksi berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
                    }
                });
    }

    /**@Override
    public void onFirebaseLoadSuccess(ArrayList<SupplierDB> supplierList) {
        daftarsupp = supplierList;
        ArrayList<String> name_list = new ArrayList<>();
        for(SupplierDB pemasok:supplierList)
            name_list.add(pemasok.getNama());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, name_list);
        spinnernamasupp.setAdapter(arrayAdapter);

    }

    @Override
    public void onFirebaseLoadFailed(String message) {

    }**/
}