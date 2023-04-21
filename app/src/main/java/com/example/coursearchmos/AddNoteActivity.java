package com.example.coursearchmos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.coursearchmos.DataBase.NoteDBHelper;
import com.example.coursearchmos.databinding.ActivityAddNoteBinding;
import com.example.coursearchmos.model.NoteModel;

public class AddNoteActivity extends AppCompatActivity {
	public static final String IDENTIFY = "com.example.coursearchmos.AddNoteActivity";
	private ActivityAddNoteBinding binding;
	private NoteDBHelper noteDBHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityAddNoteBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		noteDBHelper = new NoteDBHelper(AddNoteActivity.this);

		binding.saveButton.setOnClickListener((v ->
				saveAndBack(binding.title.getText().toString().trim()
						, binding.text.getText().toString())
		));
	}

	private void saveAndBack(String title, String text) {
		NoteModel noteModel = new NoteModel(-1, title, text, 0);
		noteDBHelper.addOne(noteModel);

		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra(MainActivity.SELECTED_FRAGMENT, IDENTIFY);
		startActivity(intent);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		noteDBHelper.close();
	}
}