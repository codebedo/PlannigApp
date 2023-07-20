package com.example.planingapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class KayitOl extends AppCompatActivity {
    // email ve şifreyi xml dosyasından almak ve tanımlamak için tanim yaptık
    EditText  email2, sifre1;
    // Burada butonumuzu belirrtik
    Button button;
    //firebase tanımı yaptık...
    FirebaseAuth mAuth;
    // Burdada ise şifremizin görünürlüğünün kontrolü için boolean olarak tanıttık
    boolean passwordVisible;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);
        //firebase bağlantısı yaptık...
        mAuth = FirebaseAuth.getInstance();

        //Burada toolbar ile geri dön butonu yaptık...
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Geri dön");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);






        button = findViewById(R.id.button2);
         email2 =  findViewById(R.id.mail2);
         sifre1 = findViewById(R.id.sifre);


        sifre1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right=2;
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if (motionEvent.getRawX()>=sifre1.getRight()
                            -sifre1.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=sifre1.getSelectionEnd();
                        if(passwordVisible){
                            sifre1.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,
                                    R.drawable.baseline_visibility_off_24,0);
                            sifre1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible=false;
                        }else{
                            sifre1.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,
                                    R.drawable.baseline_visibility_24,0);
                            sifre1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible=true;
                        }
                        if (sifre1.hasFocus()) {
                            sifre1.setSelection(selection);
                        }
                        return true;
                    }
                }
                return false;
            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = email2.getText().toString().trim();
                String password = sifre1.getText().toString().trim();
                int sifre_karakter = password.length();


            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(getApplicationContext(),
                        "Lütfen alanları boş bırakmayın", Toast.LENGTH_SHORT).show();

            }   else if(sifre_karakter < 8){
                Toast.makeText(getApplicationContext(),
                        "şifre 8 karakterden büyük olmalıdır.", Toast.LENGTH_SHORT).show();
            }  else if(!EMAIL_ADDRESS_PATTERN.matcher(email).matches()){
                Toast.makeText(getApplicationContext(),
                        "Lütfen geçerli bir eposta girin", Toast.LENGTH_SHORT).show();

            }else {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(KayitOl.this
                                ,new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                    Toast.makeText(KayitOl.this
                                            , "Kayıt başarılı.", Toast.LENGTH_SHORT).show();
                                    Intent AnaekranaGec = new Intent(KayitOl.this, E1ekrani.class);
                                    startActivity(AnaekranaGec);

                                } else {

                                    Toast.makeText(KayitOl.this
                                            , "Kayıt başarısız.", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onComplete: Failed=" + task.getException().getMessage());

                                }
                            }
                        });



            }


            }
        });




    }


    private void updateUI(FirebaseUser user) {
    }


    public static final java.util.regex.Pattern EMAIL_ADDRESS_PATTERN = java.util.regex.Pattern.compile(
            "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    );
}
