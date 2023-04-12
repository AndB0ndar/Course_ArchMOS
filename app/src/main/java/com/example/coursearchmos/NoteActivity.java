package com.example.coursearchmos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.coursearchmos.DataBase.NoteDBHelper;
import com.example.coursearchmos.databinding.ActivityNoteBinding;
import com.example.coursearchmos.model.NoteModel;

public class NoteActivity extends AppCompatActivity {
	private ActivityNoteBinding binding;
	private NoteModel noteModel;

	private NoteDBHelper noteDBAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityNoteBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		noteDBAdapter = new NoteDBHelper(NoteActivity.this);

		Bundle args = getIntent().getExtras();
		if (args != null) {
			noteModel = noteDBAdapter.getById(args.getInt(NoteModel.class.getCanonicalName()));
			// what is better to pass through the intent object or object id and get it from the database?
//			noteModel = (NoteModel) args.getSerializable(NoteModel.class.getCanonicalName());
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
		noteModel.setTitle(title);
		noteModel.setText(text);
		noteDBAdapter.updateOne(noteModel);

		Intent intent = new Intent(this, NotesActivity.class);
		startActivity(intent);
	}

	private void Remove() {
		noteDBAdapter.deleteOne(noteModel);

		Intent intent = new Intent(this, NotesActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		noteDBAdapter.close();
	}
}