package com.example.coursearchmos;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.coursearchmos.databinding.ActivityLibraryBinding;
import com.example.coursearchmos.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {
    private ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnReader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartReaderActivity(v);
            }
        });
        binding.btnLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartLibraryActivity(v);
            }
        });
        binding.btnNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartNotesActivity(v);
            }
        });
    }

    public void StartLibraryActivity(View view) {
        Intent intent = new Intent(this, LibraryActivity.class);
        startActivity(intent);
    }
    public void StartReaderActivity(View view) {
        Intent intent = new Intent(this, ReaderActivity.class);
        startActivity(intent);
    }
    public void StartNotesActivity(View view) {
        Intent intent = new Intent(this, NotesActivity.class);
        startActivity(intent);
    }
}