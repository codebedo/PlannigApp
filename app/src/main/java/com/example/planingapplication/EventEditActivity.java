package com.example.planingapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalTime;

public class EventEditActivity extends AppCompatActivity
{
    private EditText eventNameET;
    private TextView eventDateTV, eventTimeTV;

    private LocalTime time;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        // burada local tarih alındı
        time = LocalTime.now();
        // burada ise tarih ve zaman Ayarı yapıldı
        eventDateTV.setText("Tarih: " + DailyCalendarUtils.formattedDate(DailyCalendarUtils.selectedDate));
        eventTimeTV.setText("Zaman: " + DailyCalendarUtils.formattedTime(time));
    }
    // Burada ise etkinliğin adı tarihi ve zamanı için kaynak dosyası bapğlantısı yapıldı
    private void initWidgets()
    {
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeTV = findViewById(R.id.eventTimeTV);
    }
    //Burada ise girilen etkinliğin kaydedilmesi sağlandı
    public void saveEventAction(View view)
    {
        String eventName = eventNameET.getText().toString();
        DailyEvent newEvent = new DailyEvent(eventName, DailyCalendarUtils.selectedDate, time);
        DailyEvent.eventsList.add(newEvent);
        finish();
    }
}