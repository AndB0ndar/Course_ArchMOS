package com.example.coursearchmos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.LinkedList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {

	NoteAdapter noteAdapter;
	static List<String> notes = new LinkedList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notes);

		RecyclerView recyclerView = findViewById(R.id.notes_recycler_view);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		noteAdapter = new NoteAdapter(notes);
		recyclerView.setAdapter(noteAdapter);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			notes.add(extras.getString("title"));
			noteAdapter.notifyItemInserted(notes.size()-1);
//			notes.add(extras.getString("text"));
//			noteAdapter.notifyItemInserted(notes.size()-1);
		}
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