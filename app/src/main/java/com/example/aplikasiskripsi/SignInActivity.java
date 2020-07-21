package com.example.aplikasiskripsi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class SignInActivity extends AppCompatActivity {

    EditText editTextEmailLogin;
    EditText editTextPasswordLogin;
    Button buttonLogin, buttonSignUp;
    ProgressBar loading;
    FirebaseAuth firebaseAuth;
    TextView forgotpass;
    private DatabaseReference myRef;
    private FirebaseAuth.AuthStateListener AuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(this);
        loading = findViewById(R.id.loading);
        editTextEmailLogin = findViewById(R.id.editTextEmailLogin);
        editTextPasswordLogin = findViewById(R.id.editTextPasswordLogin);
        buttonLogin = findViewById(R.id.buttonLogin);
        forgotpass = findViewById(R.id.forgotpass);
        buttonSignUp = findViewById(R.id.buttonSignUp);

        AuthStateListener =  new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser == null){
                    Toast.makeText(SignInActivity.this, "Masukan email dan kata sandi",
                            Toast.LENGTH_SHORT).show();
                }
            }
        };

        buttonLogin.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(editTextEmailLogin.getText().toString(),
                        editTextPasswordLogin.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                loading.setVisibility(View.GONE);
                                if(task.isSuccessful()) {
                                    if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                        Toast.makeText(SignInActivity.this,
                                                "Anda berhasil masuk dan Email Anda sudah terverifikasi",
                                                Toast.LENGTH_LONG).show();

                                        Intent i = new Intent(SignInActivity.this,MainActivity.class);
                                        startActivity(i);
                                    }else{
                                        Toast.makeText(SignInActivity.this, "Silahkan verifikasi Email Anda",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }else{
                                    Toast.makeText(SignInActivity.this, task.getException().getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, ForgotPassActivity.class));
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, RegisterActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(AuthStateListener);}
}
