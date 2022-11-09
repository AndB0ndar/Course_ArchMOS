package com.example.coursearchmos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.coursearchmos.adapter.BookAdapter;
import com.example.coursearchmos.model.Book;

import java.util.ArrayList;
import java.util.List;


public class LibraryActivity extends AppCompatActivity {
	RecyclerView libraryRecycler;
	BookAdapter bookAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_library);

		List<Book> bookList = new ArrayList<>();
		bookList.add(new Book(0, "Евгений Онегин"));
		bookList.add(new Book(1, "Мертвые души"));
		bookList.add(new Book(2, "Война и мир"));
		bookList.add(new Book(3, "Программирование"));

		SetBookRecycler(bookList);
	}

	private void SetBookRecycler(List<Book> bookList) {
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
		libraryRecycler = findViewById(R.id.libraryRecycler);
		libraryRecycler.setLayoutManager(layoutManager);

		bookAdapter = new BookAdapter(this, bookList);
		libraryRecycler.setAdapter(bookAdapter);
	}

	public void StartReaderActivity(View view) {
		Intent intent = new Intent(this, ReaderActivity.class);
		startActivity(intent);
	}
	public void StartSettingsActivity(View view) {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}
	public void StayOnPage(View view) {
	}
	public void StartNotesActivity(View view) {
		Intent intent = new Intent(this, NotesActivity.class);
		startActivity(intent);
	}
}