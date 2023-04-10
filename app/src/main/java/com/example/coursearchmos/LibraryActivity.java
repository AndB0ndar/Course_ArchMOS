package com.example.coursearchmos;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursearchmos.DataBase.BookDBHelper;
import com.example.coursearchmos.DataBase.NoteDBHelper;
import com.example.coursearchmos.adapter.BookAdapter;
import com.example.coursearchmos.databinding.ActivityLibraryBinding;
import com.example.coursearchmos.model.Book;
import com.example.coursearchmos.model.BookModel;
import com.example.coursearchmos.model.NoteModel;

import java.util.ArrayList;
import java.util.List;


public class LibraryActivity extends AppCompatActivity {
	private static final int PICK_PDF_FILE = 2;
	private ActivityLibraryBinding binding;
	protected BookAdapter bookAdapter;
	private BookDBHelper bookDBHelper;
	static List<BookModel> books = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityLibraryBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		binding.btnAdd.setOnClickListener((v -> { openFile(); }));

//		books.add(new Book(0, "Евгений Онегин", "Пушкин"));
//		books.add(new Book(1, "Мертвые души", "Гоголь"));
//		books.add(new Book(2, "Война и мир", "Толстой"));
//		books.add(new Book(3, "Программирование", "Я"));
		bookDBHelper = new BookDBHelper(LibraryActivity.this);
		books = bookDBHelper.getAll();

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

	private void openFile() {
		Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("application/pdf");

		startActivityForResult(intent, PICK_PDF_FILE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
		super.onActivityResult(requestCode, resultCode, resultData);
		if (requestCode == PICK_PDF_FILE
				&& resultCode == Activity.RESULT_OK) {
			Uri uri = null;
			if (resultData != null) {
				uri = resultData.getData();
				bookDBHelper.addOne(new BookModel(-1, uri.getPath(), "-"));
			}
		}
	}

	private void SetBookRecycler(List<BookModel> bookList) {
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

	@Override
	protected void onResume() {
		super.onResume();

		books = bookDBHelper.getAll();
		SetBookRecycler(books);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		bookDBHelper.close();
	}
}