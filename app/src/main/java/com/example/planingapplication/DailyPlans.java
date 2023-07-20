package com.example.planingapplication;

import static com.example.planingapplication.DailyCalendarUtils.selectedDate;
import static com.example.planingapplication.DailyCalendarUtils.monthDayFromDate;
import static com.example.planingapplication.DailyCalendarUtils.monthYearFromDate;
import static com.example.planingapplication.DailyCalendarUtils.daysInMonthArray;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

public class DailyPlans extends AppCompatActivity
{

    private TextView monthDayText;
    private TextView dayOfWeekTV;
    private ListView hourListView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_plans);
        DailyCalendarUtils.selectedDate = LocalDate.now();
        initWidgets();
    }
    // burada ekranda tarihin görünmesi için gereken textlerin tanımlaması yapıldı
    private void initWidgets()
    {
        monthDayText = findViewById(R.id.monthDayText);
        dayOfWeekTV = findViewById(R.id.dayOfWeekTV);
        hourListView = findViewById(R.id.houListView);
    }
    // herhangi bir aksiyon olmaması durumunda  görünümü ayarlandı
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume()
    {
        super.onResume();
            setDayView();


    }
    // Burada alınana local tarih text'e aktarıldı ve hour adapter ile veri dolduruldu
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDayView() {
        if (selectedDate != null) {
            monthDayText.setText(DailyCalendarUtils.monthDayFromDate(selectedDate));
            String dayOfWeek = selectedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
            dayOfWeekTV.setText(dayOfWeek);
            setHourAdapter();
        } else {

        }

    }
    // Burada hour adapter ile saat liste görünümü alındı
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setHourAdapter()
    {
        HourAdapter hourAdapter = new HourAdapter(getApplicationContext(), hourEventList());
        hourListView.setAdapter(hourAdapter);
    }
    //Burada ekranımızda görülen saat listesinin matemetiksel ayarı yapıldı
    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<HourEvent> hourEventList()
    {
        ArrayList<HourEvent> list = new ArrayList<>();

        for(int hour = 0; hour < 24; hour++)
        {
            LocalTime time = LocalTime.of(hour, 0);
            ArrayList<DailyEvent> events = DailyEvent.eventsForDateAndTime(selectedDate, time);
            HourEvent hourEvent = new HourEvent(time, events);
            list.add(hourEvent);
        }

        return list;
    }
    //Burada önceki güne dönmeyi sağlayan kod yazıldı
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousDayAction(View view)
    {
        if (DailyCalendarUtils.selectedDate != null) {
            DailyCalendarUtils.selectedDate = DailyCalendarUtils.selectedDate.minusDays(1);
            setDayView();
        }
    }
    //Burada sonraki güne dönmeyi sağlayan kod yazıldı
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextDayAction(View view)
    {
        if (DailyCalendarUtils.selectedDate == null) {
            // Initialize with current date if null
            DailyCalendarUtils.selectedDate = LocalDate.now();
        }

        DailyCalendarUtils.selectedDate = DailyCalendarUtils.selectedDate.plusDays(1);
        setDayView();
    }
    //Burada etkinlik ekranını açmak için bir aksiyon oluşturuldu
    public void newEventAction(View view)
    {
        startActivity(new Intent(this, EventEditActivity.class));
    }
}