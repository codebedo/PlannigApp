package com.example.planingapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;


public class OdakZamani extends AppCompatActivity {

    // burada ekranda kullanıcağımız
    // aksiyonlar için tanımlamalar yapıldı
    private TextView counterTextView;
    private Handler handler = new Handler();
    private boolean isCountingDown = true;
    private long remainingTimeInMillis = 120 * 60 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odak_zamani);
        // burada toollbar olarak geri dön akisyonu yapıldı
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Geri dön");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        counterTextView = findViewById(R.id.counter_text_view);
        updateCounterText();
    }

    // burada sayaçın geri sayımı için gereken aksiyonlar
    // için matemetiksel hesaplamalar yapıldı
    private Runnable countDown = new Runnable() {
        @Override
        public void run() {
            if (isCountingDown) {
                remainingTimeInMillis -= 1000;
                if (remainingTimeInMillis <= 0) {
                    remainingTimeInMillis = 0;
                    isCountingDown = false;
                }
                updateCounterText();
                handler.postDelayed(this, 1000);
            }
        }
    };

    // burada sayaçın çalışması için gereken hesaplar yapıldı
    private Runnable countUp = new Runnable() {
        @Override
        public void run() {
            if (!isCountingDown) {
                remainingTimeInMillis += 1000;
                if (remainingTimeInMillis >= 120 * 60 * 1000) {
                    remainingTimeInMillis = 120 * 60 * 1000;
                }
                updateCounterText();
                handler.postDelayed(this, 1000);
            }
        }
    };

    // burada sayaçın çalışma sınıfı yazıldı
    public void startCounter(View view) {
        if (isCountingDown) {
            handler.postDelayed(countDown, 1000);
        } else {
            handler.postDelayed(countUp, 1000);
        }
    }

    // burada sayacı durduran sınıf yazıldı
    public void stopCounter(View view) {
        handler.removeCallbacks(countDown);
        handler.removeCallbacks(countUp);
    }

    // burada sayaç modunu ayarlayan sınıf yazıldı ve matemtiksel hesabı yapıldı
    public void toggleCountingMode(View view) {
        isCountingDown = !isCountingDown;
        if (isCountingDown) {
            remainingTimeInMillis = 120 * 60 * 1000;
        } else {
            remainingTimeInMillis = 0;
        }
        updateCounterText();
    }

    // burada sayacın rakamlarını düzenleyen sınıf yazıldı
    private void updateCounterText() {
        int minutes = (int) (remainingTimeInMillis / (1000 * 60));
        int seconds = (int) (remainingTimeInMillis / 1000) % 60;
        String timeText = String.format("%02d:%02d", minutes, seconds);
        counterTextView.setText(timeText);
    }
}
