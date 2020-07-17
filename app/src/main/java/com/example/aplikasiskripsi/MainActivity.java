package com.example.aplikasiskripsi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button buttonSignOut;
    FirebaseAuth firebaseAuth;
    CardView cvpenj, cvpemb, cvbrg, cvsupp;
    private FirebaseAuth.AuthStateListener AuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonSignOut = findViewById(R.id.buttonSignOut);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(this);
        cvpenj = findViewById(R.id.cvpenj);
        cvpemb = findViewById(R.id.cvpemb);
        cvbrg = findViewById(R.id.cvbrg);
        cvsupp = findViewById(R.id.cvsupp);


        cvpenj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PenjualanActivity.class);
                startActivity(i);
            }
        });

        cvpemb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PembelianActivity.class);
                startActivity(i);
            }
        });

        cvbrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, BarangActivity.class);
                startActivity(i);
            }
        });

        cvsupp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PemasokActivity.class);
                startActivity(i);
            }
        });

        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.getInstance().signOut();
                Intent i = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(i);

            }
        });
    }
}
