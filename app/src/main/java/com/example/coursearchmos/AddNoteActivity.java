package com.example.coursearchmos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.coursearchmos.databinding.ActivityAddNoteBinding;
import com.example.coursearchmos.databinding.ActivityNotesBinding;

public class AddNoteActivity extends AppCompatActivity {
	private ActivityAddNoteBinding binding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityAddNoteBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		binding.saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GoBack(binding.title.getText().toString(), binding.text.getText().toString());
			}
		});
	}

	private void GoBack(String title, String text) {
		Intent intent = new Intent(this, NotesActivity.class);
		intent.putExtra("title", title);
		intent.putExtra("text", text);
		startActivity(intent);
	}
}