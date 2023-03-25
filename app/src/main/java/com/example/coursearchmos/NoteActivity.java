package com.example.coursearchmos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.coursearchmos.databinding.ActivityNoteBinding;
import com.example.coursearchmos.model.NoteModel;

public class NoteActivity extends AppCompatActivity {
	public static final String UPDATE_MODEL = "NoteActivity.UPDATE_MODEL";
	public static final String REMOVE_MODEL = "NoteActivity.REMOVE_MODEL";
	private ActivityNoteBinding binding;
	private NoteModel noteModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityNoteBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		Bundle args = getIntent().getExtras();
		if (args != null) {
			noteModel = (NoteModel) args.getSerializable(NoteModel.class.getCanonicalName());
			binding.title.setText(noteModel.getTitle());
			binding.text.setText(noteModel.getText());
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
		noteModel.setTitle(title);
		noteModel.setText(text);
		intent.putExtra(UPDATE_MODEL, noteModel);
		startActivity(intent);
	}

	private void Remove() {
		Intent intent = new Intent(this, NotesActivity.class);
		intent.putExtra(REMOVE_MODEL, noteModel);
		startActivity(intent);
	}
}