package com.example.coursearchmos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.view.View;

import com.example.coursearchmos.databinding.ActivityBookBinding;
import com.example.coursearchmos.model.Book;
import com.github.barteksc.pdfviewer.PDFView;

public class BookActivity extends AppCompatActivity {
	private ActivityBookBinding binding;
	private Book book;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityBookBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		Bundle args = getIntent().getExtras();
		book = args.getParcelable(Book.class.getCanonicalName());

		PDFView pdfView = findViewById(R.id.pdfViewer);
		pdfView.fromAsset("latex3days.pdf").pages(0, 2, 1, 3, 3, 3).load();

		binding.back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), ReaderActivity.class);
				intent.putExtra(Book.class.getCanonicalName(), book);
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