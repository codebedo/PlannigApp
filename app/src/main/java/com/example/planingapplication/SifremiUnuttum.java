package com.example.planingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class SifremiUnuttum extends AppCompatActivity {

    private EditText formail;
    private Button forgot;
    private String email;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifremi_unuttum);
        //burada toolbar ile geri dönme butonu yaptık
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Geri dön");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);



        formail = findViewById(R.id.reset_mail);
        forgot = findViewById(R.id.reset_password);

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = formail.getText().toString();


                if(!EMAIL_ADDRESS_PATTERN.matcher(email).matches()){
                    Toast.makeText(getApplicationContext(),
                            "Lütfen geçerli bir eposta girin", Toast.LENGTH_SHORT).show();

                }else{
                        forgotPassword();
                }

            }
        });





    }

    private  void forgotPassword(){

        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Emailinizi kontrol ediniz", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SifremiUnuttum.this, E1ekrani.class));
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "Hata :", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    // Emailimizin düzgün olduğunu kontrol edebilmek için
    // ekstra olarak email pattern işlemi yaptık
    public static final java.util.regex.Pattern
            EMAIL_ADDRESS_PATTERN = java.util.regex.Pattern.compile(
            "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    );
}