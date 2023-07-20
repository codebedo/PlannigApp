package com.example.planingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class E2Anasayfa extends AppCompatActivity {

    // veri tabanına referans veriyoruz
    private DatabaseReference mDatabase;
    // burada verileri yedekliyoruz
    private List<String> buttonNames = Arrays.asList("Günlük planlar",
            "Aylık planlar", "Odak Sayaçı", "Uyarılar",
            "Haftalık Planlar", "Yapılacaklar");

   // veri tabanına referans veriyoruz
    private  DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e2_anasayfa);

        FirebaseApp.initializeApp(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Firebase veritabanından veri alma
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Anlık görüntüden verileri alıyoruz
                Object value = dataSnapshot.getValue();
                if (value instanceof Map) {
                    Map<String, String> data = (Map<String, String>) value;
                    List<String> newButtonNames = new ArrayList<>(data.values());

                    //Veriler veritabanında mevcutsa
                    if (newButtonNames != null) {
                        // buton textini güncelliyoruz
                        Button[] buttons = {findViewById(R.id.günlük_plan), findViewById(R.id.aylık_plan),
                                findViewById(R.id.zamanlayici), findViewById(R.id.uyarılar),
                                findViewById(R.id.haftalık_plan), findViewById(R.id.planlar)};
                        for (int i = 0; i < buttons.length; i++) {
                            buttons[i].setText(newButtonNames.get(i));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Veritabanı hatasını işliyoruz
            }
        };
        mDatabase.addValueEventListener(postListener);

        // verileri yedekliyoruz
        backupData();

        // Butonlara tıklama aksiyonu veriyoruz...
        Button zamanakarsi = findViewById(R.id.zamanlayici);
        zamanakarsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent zamanagec = new Intent(E2Anasayfa.this , OdakZamani.class);
                startActivity(zamanagec);
            }
        });

        //profil butonumuza aksiyon veriyoruz
        ImageView profilin = findViewById(R.id.profile_info);
        profilin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profagec = new Intent(E2Anasayfa.this, ProfileActivity.class);
                startActivity(profagec);
            }
        });
        // Butona tıklama özelliği veriyoruz
        Button todo = findViewById(R.id.planlar);
        todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent todo = new Intent(E2Anasayfa.this, Yapilacaklar.class);
                startActivity(todo);
            }
        });
        // Butona tıklama özelliği veriyoruz
        Button weeklyplans = findViewById(R.id.haftalık_plan);
        weeklyplans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent hafta = new Intent(E2Anasayfa.this, WeeklyPlans.class);
                startActivity(hafta);
            }
        });

        // Butona tıklama özelliği veriyoruz
        Button monthlyplans = findViewById(R.id.aylık_plan);
        monthlyplans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent month = new Intent(E2Anasayfa.this, MontlyhPlans.class);
                startActivity(month);
            }
        });

        // Butona tıklama özelliği veriyoruz
        Button daily = findViewById(R.id.günlük_plan);
        daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dayp = new Intent(E2Anasayfa.this, DailyPlans.class);
                startActivity(dayp);
            }
        });
    }

    // ön verileri yedekilyoruz
    private void backupData() {
        for (int i = 0; i < buttonNames.size(); i++) {
            String buttonName = buttonNames.get(i);
            mDatabase.child(String.valueOf(i)).setValue(buttonName);
        }
    }

    // Verileri geri alıyoruz
    private void revertData() {
        for (int i = 0; i < buttonNames.size(); i++) {
            String buttonName = buttonNames.get(i);
            mDatabase.child(String.valueOf(i)).setValue(buttonName);
        }
    }

    // butona basıldığında verileri geri alıyoruz
    @Override
    public void onBackPressed() {
        revertData();
        super.onBackPressed();
    }
}