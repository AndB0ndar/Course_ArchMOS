package com.example.coursearchmos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.coursearchmos.adapter.BookAdapter;
import com.example.coursearchmos.databinding.ActivityReaderBinding;
import com.example.coursearchmos.model.Book;

import java.util.ArrayList;
import java.util.List;

public class ReaderActivity extends AppCompatActivity {
	private ActivityReaderBinding binding;
	protected BookAdapter bookAdapter;
	static private Book book = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityReaderBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		// TODO: when there is no current book to read
		Bundle args = getIntent().getExtras();
		if (args != null) {
			book = args.getParcelable(Book.class.getCanonicalName());
		}
		if (book != null) {
			SetBookRecycler(book);
		}

		binding.btnSetting.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				StartSettingsActivity(v);
			}
		});
		binding.btnLibrary.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				StartLibraryActivity(v);
			}
		});
		binding.btnNotes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				StartNotesActivity(v);
			}
		});
	}

	private void SetBookRecycler(Book book) {
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
		binding.readerRecycler.setLayoutManager(layoutManager);

		List<Book> books = new ArrayList<>();
		books.add(book);
		bookAdapter = new BookAdapter(this, books);
		binding.readerRecycler.setAdapter(bookAdapter);
	}

	public void StartLibraryActivity(View view) {
		Intent intent = new Intent(this, LibraryActivity.class);
		startActivity(intent);
	}
	public void StartSettingsActivity(View view) {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}
	public void StartNotesActivity(View view) {
		Intent intent = new Intent(this, NotesActivity.class);
		startActivity(intent);
	}
}