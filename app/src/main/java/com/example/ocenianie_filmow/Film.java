package com.example.ocenianie_filmow;

import android.annotation.SuppressLint;

public class Film {
    public static final String[] genres = {"Dokumentalny", "Horror", "Thriller", "Sitcom", "Anime", "Fabularny", "Przygodowy"};
    public String name;
    public int rate;
    public String genre;

    public Film(String name, int rate, String genre) {
        this.name = name;
        this.rate = rate;
        this.genre = genre;
    }

    @SuppressLint("DefaultLocale")
    public String getFilmDetails() {
        return String.format("%s, %s - ocena: %d", name, genre, rate);
    }
}
