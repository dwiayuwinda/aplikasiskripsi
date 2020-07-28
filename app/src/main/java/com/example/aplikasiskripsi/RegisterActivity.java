package com.example.aplikasiskripsi;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity {

    EditText editTextEmail;
    EditText editTextPassword;
    Button buttonRegister;
    ProgressBar progressBarRegister;
    FirebaseAuth firebaseAuth;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private static final String USER = "User";
    private UserDB user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(this);
        progressBarRegister = findViewById(R.id.progressBarRegister);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonRegister = findViewById(R.id.buttonRegister);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(USER);

        buttonRegister.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Masukan email dan kata sandi",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                user = new UserDB(email, password);
                registerUser(email, password);
            }
        });
    }

    public void registerUser(String email, String password){
        progressBarRegister.setVisibility(View.VISIBLE);
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBarRegister.setVisibility(View.GONE);
                        if(task.isSuccessful()) {
                            firebaseAuth.getCurrentUser().sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(RegisterActivity.this,
                                                        "Akun Anda berhasil didaftar. Mohon cek email Anda untuk verifikasi",
                                                        Toast.LENGTH_LONG).show();
                                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                                updateUI(user);
                                            }else{
                                                Toast.makeText(RegisterActivity.this,
                                                        task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });

                        }else{
                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void updateUI(FirebaseUser currentUser){
        String keyId = myRef.push().getKey();
        myRef.child(keyId).setValue(user);
        Intent loginintent = new Intent(this, SignInActivity.class);
        startActivity(loginintent);
    }
}

