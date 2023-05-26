package com.example.coursearchmos;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursearchmos.DataBase.BookMarksDBAdapter;
import com.example.coursearchmos.adapter.BookMarkAdapter;
import com.example.coursearchmos.databinding.ActivityBookMarksBinding;
import com.example.coursearchmos.model.BookMarkModel;

import java.util.List;

public class BookMarksActivity extends AppCompatActivity {
	private ActivityBookMarksBinding binding;

	private BookMarksDBAdapter bookMarksDBAdapter;
	private BookMarkAdapter bookMarkAdapter;
	private int bookId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityBookMarksBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		bookMarksDBAdapter = new BookMarksDBAdapter(BookMarksActivity.this);
		Bundle args = getIntent().getExtras();
		if (args != null) {
			bookId = args.getInt(BookActivity.BOOK_ID);
			int currentPage = args.getInt(BookActivity.CURRENT_PAGE);
			binding.etPage.setText(String.valueOf(currentPage));
		}
		setBookRecycler();

		binding.btnRemove.setOnCheckedChangeListener((buttonView, isChecked)
				-> bookMarkAdapter.setFgRemove(isChecked));

		binding.btnAdd.setOnClickListener(v -> {
			String title = binding.etTitle.getText().toString();
			String s_page = binding.etPage.getText().toString();
			try {
				int page = Integer.parseInt(s_page);
				BookMarkModel bookMark = new BookMarkModel(-1, bookId, title, page);
				bookMarksDBAdapter.addOne(bookMark);
				bookMarkAdapter.setBookmarks(bookMarksDBAdapter.getAllByBookId(bookId));
				binding.etTitle.setText("");
				binding.etPage.setText("");
			} catch (NumberFormatException ignored) {
			}
		});

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle(R.string.title_bookmakrs);
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			Intent intent = new Intent(BookMarksActivity.this, BookActivity.class);
			intent.putExtra(BookActivity.BOOK_ID, bookId);
			intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
					| Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void setBookRecycler() {
		List<BookMarkModel> bookmarks = bookMarksDBAdapter.getAllByBookId(bookId);
		bookMarkAdapter = new BookMarkAdapter(BookMarksActivity.this, bookmarks);
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(BookMarksActivity.this
				, RecyclerView.VERTICAL
				, false
		);
		binding.bmRecycler.setLayoutManager(layoutManager);
		binding.bmRecycler.setAdapter(bookMarkAdapter);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		binding = null;
		bookMarksDBAdapter.close();
	}
}