package com.example.planingapplication;


import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

// Burada bu sınıf için Hour event sınıfın kalıtım alındı
public class HourAdapter extends ArrayAdapter<HourEvent>
{
    // Burada etkinlit listesinin baplantısı yapıldı
    public HourAdapter(@NonNull Context context, List<HourEvent> hourEvents)
    {
        super(context, 0, hourEvents);
    }
    // Bu method içerisinde ise alınan etkinliklerinin kaynak dosyası ile item bağlantıları oluşturuldu
    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        HourEvent event = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.hour_cell, parent, false);

        setHour(convertView, event.time);
        setDailyEvents(convertView, event.events);

        return convertView;
    }
    // Burada ise zaman formatı yapıldı ve doldurulması sağlandı
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setHour(View convertView, LocalTime time)
    {
        TextView timeTV = convertView.findViewById(R.id.timeTV);
        timeTV.setText(DailyCalendarUtils.formattedShortTime(time));
    }
    // Burada ise event parçalarının kaynak dosyası ile bağlantısı sağlandı
    private void setDailyEvents(View convertView, ArrayList<DailyEvent> events)
    {
        TextView event1 = convertView.findViewById(R.id.event1);
        TextView event2 = convertView.findViewById(R.id.event2);
        TextView event3 = convertView.findViewById(R.id.event3);
        // Burada eventların durumu kontrol edilip ona göre görünüm kazanması sağlandı
        if(events.size() == 0)
        {
            hideDailyEvent(event1);
            hideDailyEvent(event2);
            hideDailyEvent(event3);
        }
        else if(events.size() == 1)
        {
            setDailyEvent(event1, events.get(0));
            hideDailyEvent(event2);
            hideDailyEvent(event3);
        }
        else if(events.size() == 2)
        {
            setDailyEvent(event1, events.get(0));
            setDailyEvent(event2, events.get(1));
            hideDailyEvent(event3);
        }
        else if(events.size() == 3)
        {
            setDailyEvent(event1, events.get(0));
            setDailyEvent(event2, events.get(1));
            setDailyEvent(event3, events.get(2));
        }
        else
        {
            setDailyEvent(event1, events.get(0));
            setDailyEvent(event2, events.get(1));
            event3.setVisibility(View.VISIBLE);
            String eventsNotShown = String.valueOf(events.size() - 2);
            eventsNotShown += " More Events";
            event3.setText(eventsNotShown);
        }
    }
    // Burada ise alınan aksiyona göre eventların görünebilmesi ve doldurulması sağlandı
    private void setDailyEvent(TextView textView, DailyEvent event)
    {
        textView.setText(event.getName());
        textView.setVisibility(View.VISIBLE);
    }
    //burada ise eventların görünümünü sağlayan method oluşturuldu
    private void hideDailyEvent(TextView tv)
    {
        tv.setVisibility(View.INVISIBLE);
    }

}