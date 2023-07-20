package com.example.planingapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalTime;

public class WeeklyEventEdit extends AppCompatActivity {
    private EditText eventNameET;
    private TextView eventDateTV, eventTimeTV;

    private LocalTime time;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_event_edit);

        //burada oluşturduğumuz methodu çağırıldı
        initWidgets();
        //anlık tarihi alındı
        time = LocalTime.now();
        // burada seçilen tarih ve sürenin anlık görünümünü tanımlandı
        eventDateTV.setText("Tarih: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        eventTimeTV.setText("Zaman: " + CalendarUtils.formattedTime(time));

    }
    // oluşturduğumuz methodda kaynak kodlar ile bağlantı kuruldu
    private void initWidgets()
    {
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeTV = findViewById(R.id.eventTimeTV);
    }

    // Etkimlik aksiyonunda kayıt işlemi gerçekleştirildi
    public void saveEventAction(View view)
    {
        String eventName = eventNameET.getText().toString();
        Event newEvent = new Event(eventName, CalendarUtils.selectedDate, time);
        Event.eventsList.add(newEvent);
        finish();
    }
}