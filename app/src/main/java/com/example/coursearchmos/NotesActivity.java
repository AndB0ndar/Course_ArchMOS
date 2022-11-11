package com.example.coursearchmos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.coursearchmos.adapter.BookAdapter;
import com.example.coursearchmos.adapter.NoteAdapter;
import com.example.coursearchmos.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {
	RecyclerView notesRecycler;
	NoteAdapter noteAdapter;
	static List<Note> notes = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notes);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			Note note = new Note(notes.size()+1, extras.getString("title"), extras.getString("text"));
			notes.add(note);
		}
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
		notesRecycler = findViewById(R.id.notesRecycler);
		notesRecycler.setLayoutManager(layoutManager);

		noteAdapter = new NoteAdapter(this, notes);
		notesRecycler.setAdapter(noteAdapter);
	}
	public void StartLibraryActivity(View view) {
		Intent intent = new Intent(this, LibraryActivity.class);
		startActivity(intent);
	}
	public void StartReaderActivity(View view) {
		Intent intent = new Intent(this, ReaderActivity.class);
		startActivity(intent);
	}
	public void StayOnPage(View view) {
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