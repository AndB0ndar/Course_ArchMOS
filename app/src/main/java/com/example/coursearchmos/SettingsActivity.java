package com.example.coursearchmos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }
    public void StartLibraryActivity(View view) {
        Intent intent = new Intent(this, LibraryActivity.class);
        startActivity(intent);
    }
    public void StartReaderActivity(View view) {
        Intent intent = new Intent(this, ReaderActivity.class);
        startActivity(intent);
    }
    public void StayOnPage(View view) {
    }
    public void StartNotesActivity(View view) {
        Intent intent = new Intent(this, NotesActivity.class);
        startActivity(intent);
    }
}