package com.example.planingapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    TextView name, email;
 //   ImageView userImage;
    Button Logout;

    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Burada toolbar ile geri dön butonu yapıldı...
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Geri dön");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.user_name);
        email = findViewById(R.id.user_email);
     //   userImage = findViewById(R.id.user_image);
        Logout = findViewById(R.id.logout_btn);
        // Burada firebase isle veritabanı işlemleri yapıldı...
        user = FirebaseAuth.getInstance().getCurrentUser();
        // veritabanından alınan verilerin doldurulması sağlandı...
        name.setText(user.getDisplayName());
        email.setText(user.getEmail());
       // Glide.with(ProfileActivity.this).load(user.getPhotoUrl()).into(userImage);

        // burada Hesap çıkış butonu ile veritabanına hesaptan çıkış biligisi verildi
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               FirebaseAuth.getInstance().signOut();
               startActivity(new Intent(ProfileActivity.this,E1ekrani.class));
               finish();
            }
        });



    }
}