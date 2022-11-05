package com.example.coursearchmos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddNoteActivity extends AppCompatActivity {
	EditText title;
	EditText text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_note);
		title = findViewById(R.id.title);
		text = findViewById(R.id.text);
	}

	public void Save(View view) {
		GoBack(title.getText().toString(), text.getText().toString());
	}

	private void GoBack(String title, String text) {
		Intent intent = new Intent(this, NotesActivity.class);
		intent.putExtra("title", title);
		intent.putExtra("text", text);
		startActivity(intent);
	}
}