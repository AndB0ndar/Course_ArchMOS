package com.example.coursearchmos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.coursearchmos.databinding.ActivityBookBinding;
import com.example.coursearchmos.databinding.ActivityNoteBinding;
import com.example.coursearchmos.databinding.ActivityNotesBinding;
import com.example.coursearchmos.model.Book;
import com.example.coursearchmos.model.Note;

public class NoteActivity extends AppCompatActivity {
	private ActivityNoteBinding binding;
	private int id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityNoteBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		Bundle args = getIntent().getExtras();
		if (args != null) {
			Note note = args.getParcelable(Note.class.getCanonicalName());
			id = args.getInt("id");
			binding.title.setText(note.getTitle());
			binding.text.setText(note.getText());
		} else {
			GoBack();
		}

		binding.saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Save(binding.title.getText().toString(), binding.text.getText().toString());
			}
		});

		binding.removeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Remove();
			}
		});
	}

	private void GoBack() {
		Intent intent = new Intent(this, NotesActivity.class);
		startActivity(intent);
	}

	private void Save(String title, String text) {
		Intent intent = new Intent(this, NotesActivity.class);
		intent.putExtra("id", id);
		intent.putExtra("title", title);
		intent.putExtra("text", text);
		startActivity(intent);
	}

	private void Remove() {
		Intent intent = new Intent(this, NotesActivity.class);
		intent.putExtra("remove", true);
		intent.putExtra("id", id);
		startActivity(intent);
	}
}