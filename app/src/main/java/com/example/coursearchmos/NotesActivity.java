package com.example.coursearchmos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.coursearchmos.adapter.BookAdapter;
import com.example.coursearchmos.adapter.NoteAdapter;
import com.example.coursearchmos.databinding.ActivityLibraryBinding;
import com.example.coursearchmos.databinding.ActivityNotesBinding;
import com.example.coursearchmos.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {
	private ActivityNotesBinding binding;
	protected NoteAdapter noteAdapter;
	static List<Note> notes = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityNotesBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			int id = extras.getInt("id", -1);
			if (id != -1) {
				if (extras.getBoolean("remove", false))
					notes.remove(id);
				else {
					notes.remove(id);
					String title = extras.getString("title");
					String text = extras.getString("text");
					Note note = new Note(extras.getString("title"), extras.getString("text"));
					notes.add(note);
				}
			} else {
				Note note = new Note(extras.getString("title"), extras.getString("text"));
				notes.add(note);
			}
		}
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
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
	public void AddNote(View view) {
		Intent intent = new Intent(this, AddNoteActivity.class);
		startActivity(intent);
	}
}