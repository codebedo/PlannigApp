package com.example.planingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class E1ekrani extends AppCompatActivity {

    //Burda şifre görüntülenmesini kontrol edbilmek için bir boolean belirttik
    boolean passwordVisible;
    // email ve şifreyi xml dosyasından almak ve tanımlamak için tanim yaptık
    EditText email, sifre;

    // firebase girişi için bir tanim yaptık
    FirebaseAuth mAuth;

    // diğer ekranlara geçişi sağlayan textlere tanım verdik
    TextView sifreU, hesapolusturma;
    // butttonumuzu tanımladık
    Button giris;

    // burada ise firebase kayıtlı olan kullanıcı denetimi yaptık
    @Override
    public  void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){

            Intent gec = new Intent(E1ekrani.this, E2Anasayfa.class);
            startActivity(gec);
            finish();

        }

    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e1ekrani);

        // firebase tanımını yaptık
        mAuth = FirebaseAuth.getInstance();
        // Burada tasarımda kullandığımız layout parçalarını bağlantısı yapıyoruz...
        giris = findViewById(R.id.girise1);
        EditText mail = findViewById(R.id.maile1);
        EditText  sifre =  findViewById(R.id.sifree1);
        sifreU = findViewById(R.id.sifreunuttum);
        hesapolusturma = findViewById(R.id.hesapyap);

        // burada yaptığımız işlem şifre girerken şifrenin görüntülenmesini kontrol ediyoruz...
        sifre.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right=2;
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if (motionEvent.getRawX()>=sifre.getRight()-sifre.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=sifre.getSelectionEnd();
                        if(passwordVisible){
                            sifre.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,
                                    R.drawable.baseline_visibility_off_24,0);
                            sifre.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible=false;
                        }else{
                            sifre.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,
                                    R.drawable.baseline_visibility_24,0);
                            sifre.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible=true;
                        }
                        if (sifre.hasFocus()) {
                            sifre.setSelection(selection);
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        // burada tıklanma özelliğini verdiğimiz layout parçalrına sayfa açabilme fonksiyonunu sağlıyoruz...
        sifreU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gecisYap = new Intent(E1ekrani.this, SifremiUnuttum.class);
                startActivity(gecisYap);
            }
        });

        hesapolusturma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gecis = new Intent(E1ekrani.this, KayitOl.class);
                startActivity(gecis);
            }
        });

        //burada giriş butonumuza tıklanma özelliği verdik
        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* email ve passwordu string olarak tanımlayarak if else ile
                kullanıcı girişinin hatalı olabilicek kontrollerini yaptık.
                 */
                    String email, password;
                 email =String.valueOf( mail.getText());
                 password = String.valueOf(sifre.getText());

                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(),
                            "Lütfen alanları boş bırakmayın", Toast.LENGTH_SHORT).show();


                }else if(!EMAIL_ADDRESS_PATTERN.matcher(email).matches()){
                    Toast.makeText(getApplicationContext(),
                            "Lütfen geçerli bir eposta girin", Toast.LENGTH_SHORT).show();

                }else if(password.length()<8){
                    Toast.makeText(getApplicationContext(),
                            "Şifre 8 karakterden büyük olmalıdır", Toast.LENGTH_SHORT).show();
                }
                else {
                    // bur kod bloğunda firebase kullanıcı kontrolü yaptıl.
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(E1ekrani.this
                                                ,"Giriş başarılı", Toast.LENGTH_SHORT).show();
                                        // eğer başarılı olunursa Bizi ana sayfaya akrtarıcak
                                        Intent gec = new Intent(E1ekrani.this
                                                , E2Anasayfa.class);
                                        startActivity(gec);
                                        finish();
                                    }else{
                                        Toast.makeText(E1ekrani.this
                                                ,"Giriş kabul edilmedi", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });




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
