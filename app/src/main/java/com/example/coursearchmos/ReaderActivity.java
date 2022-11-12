package com.example.coursearchmos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.coursearchmos.data.CurrentReadBook;
import com.example.coursearchmos.databinding.ActivityLibraryBinding;
import com.example.coursearchmos.databinding.ActivityReaderBinding;
import com.example.coursearchmos.model.Book;

public class ReaderActivity extends AppCompatActivity {
	private ActivityReaderBinding binding;
	static private Book book;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityReaderBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		// TODO: when there is no current book to read
		book = CurrentReadBook.getBook();
		if (book != null) {
			binding.bookTitle.setText(book.getTitle());
			binding.bookAuthor.setText(book.getAuthor());
			binding.bookImage.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(v.getContext(), BookActivity.class);
					v.getContext().startActivity(intent);
				}
			});
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