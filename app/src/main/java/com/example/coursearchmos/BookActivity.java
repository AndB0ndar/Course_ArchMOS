package com.example.coursearchmos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.coursearchmos.data.CurrentReadBook;
import com.example.coursearchmos.databinding.ActivityBookBinding;
import com.example.coursearchmos.databinding.ActivityLibraryBinding;
import com.example.coursearchmos.model.Book;

public class BookActivity extends AppCompatActivity {
	private ActivityBookBinding binding;
	private Book book;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityBookBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		book = CurrentReadBook.getBook();

		binding.back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), ReaderActivity.class);
				startActivity(intent);
			}
		});
		binding.info.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String text = "Название:\n" + book.getTitle()
						+ "\nАвтор:\n" + book.getAuthor();
				ShowInfo(text);
			}
		});
	}

	private void ShowInfo(String text) {
		AlertDialog.Builder builder = new AlertDialog.Builder(BookActivity.this);
		builder.setTitle("Информация о книге")
				.setMessage(text)
				.setCancelable(true)
				.setPositiveButton("Хорошо", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		AlertDialog dialog = builder.create();
		dialog.show();
	}
}