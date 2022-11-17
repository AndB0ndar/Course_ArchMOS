package com.example.coursearchmos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.coursearchmos.adapter.BookAdapter;
import com.example.coursearchmos.databinding.ActivityLibraryBinding;
import com.example.coursearchmos.model.Book;

import java.util.ArrayList;
import java.util.List;


public class LibraryActivity extends AppCompatActivity {
	private ActivityLibraryBinding binding;
	protected BookAdapter bookAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityLibraryBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		List<Book> books = new ArrayList<>();
		books.add(new Book(0, "Евгений Онегин", "Пушкин"));
		books.add(new Book(1, "Мертвые души", "Гоголь"));
		books.add(new Book(2, "Война и мир", "Толстой"));
		books.add(new Book(3, "Программирование", "Я"));

		SetBookRecycler(books);

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
		binding.btnNotes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				StartNotesActivity(v);
			}
		});
	}

	private void SetBookRecycler(List<Book> bookList) {
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
		binding.libraryRecycler.setLayoutManager(layoutManager);

		bookAdapter = new BookAdapter(this, bookList);
		binding.libraryRecycler.setAdapter(bookAdapter);
	}

	public void StartReaderActivity(View view) {
		Intent intent = new Intent(this, ReaderActivity.class);
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