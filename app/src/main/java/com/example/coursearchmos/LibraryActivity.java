package com.example.coursearchmos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;


public class LibraryActivity extends AppCompatActivity {
	private RecyclerView rvBook;
	private static final int REQUEST_PERMISSION = 101;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_library);
		rvBook = findViewById(R.id.rv_books);
		rvBook.setLayoutManager(new LinearLayoutManager(this));

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			// SDK_INT -> The SDK version of the software currently running on this hardware device
			checkPermission();
		} else {
			generateList();
		}
	}

	@TargetApi(Build.VERSION_CODES.M)
	private void checkPermission() {  // request for permission
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
				== PackageManager.PERMISSION_GRANTED) {
			generateList();
		} else {
			ActivityCompat.requestPermissions(this,
					new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, REQUEST_PERMISSION);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode  // Getting the result of the request
			,@NonNull String[] permissions
			,@NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == REQUEST_PERMISSION) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				generateList();
			}
		}
	}

	private void generateList() {
		return;
	}

	public void StartReaderActivity(View view) {
		Intent intent = new Intent(this, ReaderActivity.class);
		startActivity(intent);
	}
	public void StartSettingsActivity(View view) {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}
	public void StayOnPage(View view) {
	}
	public void StartNotesActivity(View view) {
		Intent intent = new Intent(this, NotesActivity.class);
		startActivity(intent);
	}
}