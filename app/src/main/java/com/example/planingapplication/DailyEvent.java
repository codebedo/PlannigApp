package com.example.planingapplication;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;


public class DailyEvent  {

    //Burada etkinliklerin alınması ve listeye eklenme işlemi yapıldı...
    public static ArrayList<DailyEvent> eventsList = new ArrayList<>();

    public static ArrayList<DailyEvent> eventsForDate(LocalDate date)
    {
        ArrayList<DailyEvent> events = new ArrayList<>();
        for (DailyEvent event : eventsList) {


            if (event.getDate().equals(date)){
                events.add(event);
            }

        }

        return events;
    }

    //Burada seçilen saate göre etkinlik eklendi...
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static  ArrayList<DailyEvent> eventsForDateAndTime(LocalDate date , LocalTime time){
        ArrayList<DailyEvent> events = new ArrayList<>();

        for(DailyEvent event : eventsList){
            int eventHour = event.time.getHour();
            int cellHour = time.getHour();
            if(event.getDate().equals(date) && eventHour == cellHour)
                events.add(event);
        }

        return  events;

    }
    // Burada ise etkinliğin adı tarihi ve zamanı tnaımları yapıldı
    private  String name;
    private LocalDate date;
    private LocalTime time;

    public DailyEvent(String name, LocalDate date, LocalTime time)

    {
        this.name = name;
        this.date = date;
        this.time = time;

    }

    public String getName()
    {
        return name;

    }

    public void setName(String name){
        this.name = name;
    }

    public LocalDate getDate(){
        return date;
    }

    public LocalTime getTime(){
        return time;
    }
    public void setTime(LocalTime time)
    {
        this.time = time;

    }
}
