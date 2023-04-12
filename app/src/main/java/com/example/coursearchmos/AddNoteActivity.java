package com.example.coursearchmos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.coursearchmos.DataBase.NoteDBHelper;
import com.example.coursearchmos.databinding.ActivityAddNoteBinding;
import com.example.coursearchmos.model.NoteModel;

public class AddNoteActivity extends AppCompatActivity {
	private ActivityAddNoteBinding binding;
	private NoteDBHelper noteDBAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityAddNoteBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		noteDBAdapter = new NoteDBHelper(AddNoteActivity.this);

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
		NoteModel noteModel = new NoteModel(-1, title, text);
		noteDBAdapter.addOne(noteModel);

		Intent intent = new Intent(this, NotesActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		noteDBAdapter.close();
	}
}