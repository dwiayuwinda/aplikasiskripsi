package com.example.aplikasiskripsi;

import android.content.Context;
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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.text.TextUtils.isEmpty;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InputSuppFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InputSuppFragment extends Fragment {
    EditText edtTextkdsupp;
    EditText edtTextnamasupp;
    EditText edtTextalamat;
    EditText edtTexttelp;
    Button btnSimpan, btnHapus;
    private DatabaseReference myRef;
    private FirebaseDatabase fireIns;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InputSuppFragment() { }

    public static InputSuppFragment newInstance(String param1, String param2) {
        InputSuppFragment fragment = new InputSuppFragment();
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

        View view = inflater.inflate(R.layout.fragment_input_supp, container, false);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        edtTextkdsupp = view.findViewById(R.id.edtTextkdsupp);
        edtTextnamasupp = view.findViewById(R.id.edtTextnamasupp);
        edtTextalamat = view.findViewById(R.id.edtTextalamat);
        edtTexttelp = view.findViewById(R.id.edtTexttelp);
        btnSimpan = view.findViewById(R.id.btnSimpan);
        btnHapus = view.findViewById(R.id.btnHapus);

        FirebaseApp.initializeApp(getActivity());
        fireIns = FirebaseDatabase.getInstance();
        myRef = fireIns.getInstance().getReference();
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isEmpty(edtTextkdsupp.getText().toString())
                        && !isEmpty(edtTextnamasupp.getText().toString())
                        && !isEmpty(edtTextalamat.getText().toString())
                        && !isEmpty(edtTexttelp.getText().toString()))
                    submitSupplier(new SupplierDB(edtTextkdsupp.getText().toString(),
                            edtTextnamasupp.getText().toString(),
                            edtTextalamat.getText().toString(),
                            edtTexttelp.getText().toString()));
                else
                    Snackbar.make(btnSimpan, "Data pemasok tidak boleh kosong",
                            Snackbar.LENGTH_LONG).show();

                InputMethodManager imm = (InputMethodManager) getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(
                        edtTextkdsupp.getWindowToken(), 0);
            }
        });
        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTextkdsupp.setText(" ");
                edtTextnamasupp.setText(" ");
                edtTextalamat.setText(" ");
                edtTexttelp.setText(" ");

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

    private void submitSupplier(SupplierDB supplier) {
        /**
         * Ini adalah kode yang digunakan untuk mengirimkan data ke Firebase Realtime Database
         * dan juga kita set onSuccessListener yang berisi kode yang akan dijalankan
         * ketika data berhasil ditambahkan
         */
        myRef.child("Pemasok")
                .push()
                .setValue(supplier)
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        edtTextkdsupp.setText("");
                        edtTextnamasupp.setText("");
                        edtTextalamat.setText("");
                        edtTexttelp.setText("");
                        Snackbar.make(btnSimpan, "Data berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
                    }
                });
    }
}