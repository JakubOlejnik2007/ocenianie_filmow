package com.example.ocenianie_filmow;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Film> arrayOfFilms = new ArrayList<>();
    private EditText nameEdit;
    private EditText rateEdit;
    private Spinner genreSpinner;
    private ListView listView;
    private Switch sortingSwitch;
    private boolean sortingType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.nameEdit = findViewById(R.id.name);
        this.rateEdit = findViewById(R.id.rate);
        this.genreSpinner = findViewById(R.id.genre);
        Button submitButton = findViewById(R.id.submitButton);
        this.listView = findViewById(R.id.listView);
        this.sortingSwitch = findViewById(R.id.sortingSwitch);
        sortingType = sortingSwitch.isChecked();
        this.arrayOfFilms = SharedPreferencesManager.loadFilmList(MainActivity.this);
        addItemsToSpinner(this.genreSpinner, Film.genres);
        submitButton.setOnClickListener(this::handleAddNewFilmReview);
        this.sortingSwitch.setOnCheckedChangeListener(this::switchChanged);

        sortAndDisplayArrayOfFilms();
    }

    private void switchChanged(CompoundButton compoundButton, boolean b) {
        this.sortingType = sortingSwitch.isChecked();
        sortAndDisplayArrayOfFilms();
    }

    private void addItemsToSpinner(Spinner spinner, String[] items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
    }

    private void sortAndDisplayArrayOfFilms() {
        if (this.sortingType) {
            arrayOfFilms.sort(Comparator.comparingInt(film -> film.rate));
        } else {
            arrayOfFilms.sort(Comparator.comparing(film -> film.name));
        }
        displayArray();
    }

    private void handleAddNewFilmReview(View view) {
        // Validation of inputs
        if (!validateNameInput(this.nameEdit) || !validateRateInput(this.rateEdit)) {
            displayDialog("Wprowadzono nieprawidłowe dane! Spróbuj jeszcze raz");
            return;
        }

        String name =  this.nameEdit.getText().toString().trim();
        int rate = Integer.parseInt(this.rateEdit.getText().toString().trim());
        String genre = this.genreSpinner.getSelectedItem().toString().trim();

        Film newFilm = new Film(name, rate, genre);
        this.arrayOfFilms.add(newFilm);

        sortAndDisplayArrayOfFilms();
        clearInputs();
        SharedPreferencesManager.saveFilmList(MainActivity.this, this.arrayOfFilms);
    }

    private void clearInputs() {
        this.nameEdit.setText("");
        this.rateEdit.setText("");
    }

    private void displayArray() {
        ArrayList<String> toDisplay = new ArrayList<>();
        for (Film film : this.arrayOfFilms)
            toDisplay.add(film.getFilmDetails());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, toDisplay);
        this.listView.setAdapter(adapter);
    }

    private boolean validateRateInput(EditText editText) {
        String value = editText.getText().toString().trim();
        if(value.isEmpty()) return false;

        int rate = Integer.parseInt(value);
        return rate >= 1 && rate <= 5;
    }

    private boolean validateNameInput(EditText editText) {
        return !editText.getText().toString().trim().isEmpty();
    }

    private void displayDialog (String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(message)
                .setTitle("Oceń filmy! - Komunikat")
                .setPositiveButton("OK", (d, i) -> {});
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}