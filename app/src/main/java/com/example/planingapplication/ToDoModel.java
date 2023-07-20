package com.example.planingapplication;

// taskıd den kalıtım alındı
public class ToDoModel extends TaskId {

    private String task , due;
    private int status;

    // Veri modeli yapıldı
    // görev modeli yapıldı
    public String getTask() {
        return task;
    }
    // tarih modeli yapıldı
    public String getDue() {
        return due;
    }

    // durum modeli yapıldı
    public int getStatus() {
        return status;
    }
}