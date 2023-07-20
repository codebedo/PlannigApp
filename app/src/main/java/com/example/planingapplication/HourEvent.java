package com.example.planingapplication;

import java.time.LocalTime;
import java.util.ArrayList;

class HourEvent
{
    LocalTime time;
    ArrayList<DailyEvent> events;
    // Burada etkinlikler alındığı zaman saat etkinliği için listeye aktarma ve tanımlama yapıldı
    public HourEvent(LocalTime time, ArrayList<DailyEvent> events)
    {
        this.time = time;
        this.events = events;
    }
    // Burada ise saatlik alınan etkinlikler için tarih ve tkinliler dönüş değeri verildi ve
    // Bunun için ayrıca method oluştuldu
    public LocalTime getTime()
    {
        return time;
    }

    public void setTime(LocalTime time)
    {
        this.time = time;
    }

    public ArrayList<DailyEvent> getEvents()
    {
        return events;
    }

    public void setEvents(ArrayList<DailyEvent> events)
    {
        this.events = events;
    }
}