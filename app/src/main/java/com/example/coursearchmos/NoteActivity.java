package com.example.coursearchmos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.coursearchmos.DataBase.NoteDBAdapter;
import com.example.coursearchmos.databinding.ActivityNoteBinding;
import com.example.coursearchmos.model.NoteModel;

public class NoteActivity extends AppCompatActivity {
	public static final String IDENTIFY = "com.example.coursearchmos.NoteActivity";
	private ActivityNoteBinding binding;
	private NoteModel noteModel;

	private NoteDBAdapter noteDBHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityNoteBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		noteDBHelper = new NoteDBAdapter(NoteActivity.this);

		Bundle args = getIntent().getExtras();
		if (args != null) {
			noteModel = noteDBHelper.getById(args.getInt(NoteModel.class.getCanonicalName()));
			binding.title.setText(noteModel.getTitle());
			binding.text.setText(noteModel.getText());
		} else {
			back();
		}

		binding.saveButton.setOnClickListener((v ->
				save(binding.title.getText().toString(), binding.text.getText().toString())
		));
		binding.removeButton.setOnClickListener((v -> remove()));
	}

	private void back() {
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra(MainActivity.SELECTED_FRAGMENT, IDENTIFY);
		startActivity(intent);
	}

	private void save(String title, String text) {
		noteModel.setTitle(title);
		noteModel.setText(text);
		noteDBHelper.updateOne(noteModel);

		back();
	}

	private void remove() {
		noteDBHelper.deleteOne(noteModel);

		back();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		noteDBHelper.close();
	}
}