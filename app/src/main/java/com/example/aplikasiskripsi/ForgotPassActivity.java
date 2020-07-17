package com.example.aplikasiskripsi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassActivity extends AppCompatActivity {
    EditText emailfp;
    Button buttonfp;
    ProgressBar progressBarfp;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(this);
        progressBarfp = findViewById(R.id.progressBarfp);
        emailfp = findViewById(R.id.emailfp);
        buttonfp = findViewById(R.id.buttonfp);

        buttonfp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarfp.setVisibility(View.VISIBLE);
                firebaseAuth.sendPasswordResetEmail(emailfp.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressBarfp.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                            Toast.makeText(ForgotPassActivity.this,
                                    "Kata Sandi telah dikirim ke Email", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(ForgotPassActivity.this,
                                    task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                        });


            }
        });


    }
}
