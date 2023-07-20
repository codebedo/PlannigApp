package com.example.planingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bu kod bloğu bizi kullanıcı girişi olan E1ekranina 3 saniye sonra aktarır...
        Thread timerThread = new Thread(){ public void run(){
            try { sleep(3000);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }finally {
                Intent intent = new Intent(Splash.this, E1ekrani.class);
                startActivity(intent);
                finish();
            }
        }
        };
        timerThread.start();
    }
}