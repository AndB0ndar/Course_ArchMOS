package com.example.coursearchmos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ReaderActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reader);
	}

	public void StartLibraryActivity(View view) {
		Intent intent = new Intent(this, LibraryActivity.class);
		startActivity(intent);
	}
	public void StartSettingsActivity(View view) {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}
	public void StayOnPage(View view) {
		return;
	}
}