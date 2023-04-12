package com.example.coursearchmos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.coursearchmos.DataBase.NoteDBHelper;
import com.example.coursearchmos.adapter.NoteAdapter;
import com.example.coursearchmos.databinding.ActivityNotesBinding;
import com.example.coursearchmos.model.NoteModel;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {
	private ActivityNotesBinding binding;
	protected NoteAdapter noteAdapter;

	private NoteDBHelper noteDBAdapter;

	static List<NoteModel> notes = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityNotesBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		noteDBAdapter = new NoteDBHelper(NotesActivity.this);
		notes = noteDBAdapter.getAll();

		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this
				, RecyclerView.VERTICAL
				, false
		);
		binding.notesRecycler.setLayoutManager(layoutManager);
		noteAdapter = new NoteAdapter(this, notes);
		binding.notesRecycler.setAdapter(noteAdapter);

		binding.btnSetting.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				StartSettingsActivity(v);
			}
		});
		binding.btnReader.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				StartReaderActivity(v);
			}
		});
		binding.btnLibrary.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				StartLibraryActivity(v);
			}
		});

		// create note
		binding.btnAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AddNote(v);
			}
		});
	}

	public void StartLibraryActivity(View view) {
		Intent intent = new Intent(this, LibraryActivity.class);
		startActivity(intent);
	}

	public void StartReaderActivity(View view) {
		Intent intent = new Intent(this, ReaderActivity.class);
		startActivity(intent);
	}

	public void StartSettingsActivity(View view) {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}

	//create note
	public void AddNote(View view) {
		Intent intent = new Intent(this, AddNoteActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		noteDBAdapter.close();
	}
}