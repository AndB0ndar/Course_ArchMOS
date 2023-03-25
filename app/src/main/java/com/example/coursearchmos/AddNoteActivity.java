package com.example.coursearchmos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.coursearchmos.databinding.ActivityAddNoteBinding;
import com.example.coursearchmos.model.NoteModel;

public class AddNoteActivity extends AppCompatActivity {
	public static final String CREATE_MODEL = "CREATE_MODEL";
	private ActivityAddNoteBinding binding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityAddNoteBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		binding.saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				saveAndBack(binding.title.getText().toString().trim()
						, binding.text.getText().toString()
				);
			}
		});
	}

	private void saveAndBack(String title, String text) {
		Intent intent = new Intent(this, NotesActivity.class);
		NoteModel noteModel = new NoteModel(-1, title, text);
		intent.putExtra(CREATE_MODEL, noteModel);
		startActivity(intent);
	}
}