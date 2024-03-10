package com.example.ocenianie_filmow;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class SharedPreferencesManager {

    private static final String PREF_NAME = "FilmListPrefs";

    public static void saveFilmList(Context context, ArrayList<Film> filmList) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        for (int i = 0; i < filmList.size(); i++) {
            Film film = filmList.get(i);
            editor.putString("film_name_" + i, film.name);
            editor.putInt("film_rate_" + i, film.rate);
            editor.putString("film_genre_" + i, film.genre);
        }

        editor.apply();
    }

    public static ArrayList<Film> loadFilmList(Context context) {
        ArrayList<Film> filmList = new ArrayList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        int i = 0;
        while (true) {
            String filmName = sharedPreferences.getString("film_name_" + i, null);
            if (filmName == null) break; // Brak klucza, przerwij pętlę

            int filmRate = sharedPreferences.getInt("film_rate_" + i, 1);
            String filmGenre = sharedPreferences.getString("film_genre_" + i, "");

            Film film = new Film(filmName, filmRate, filmGenre);
            filmList.add(film);
            i++;
        }

        return filmList;
    }
}
