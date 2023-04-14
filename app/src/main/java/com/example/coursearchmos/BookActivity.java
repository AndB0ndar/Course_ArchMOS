package com.example.coursearchmos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.coursearchmos.DataBase.BookDBHelper;
import com.example.coursearchmos.databinding.ActivityBookBinding;
import com.example.coursearchmos.model.BookModel;
import com.example.coursearchmos.model.NoteModel;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class BookActivity extends AppCompatActivity {
	private ActivityBookBinding binding;
	private BookModel bookModel;
	private BookDBHelper bookDBHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityBookBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		bookDBHelper = new BookDBHelper(BookActivity.this);

		Bundle args = getIntent().getExtras();
		bookModel = bookDBHelper.getById(args.getInt(BookModel.class.getCanonicalName()));
		PDFView pdfView = findViewById(R.id.pdfViewer);
		pdfView.fromFile(new File(bookModel.getPath())).pages(0, 1, 2, 3).load();
//		pdfView.fromUri(Uri.parse(bookModel.getSUri())).pages(0, 1, 2, 3).load();
//		pdfView.fromBytes(bookModel.getBytes());

		binding.back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), ReaderActivity.class);
				intent.putExtra(BookModel.class.getCanonicalName(), bookModel.getId());
				startActivity(intent);
			}
		});
		binding.info.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String text = "Название:\n" + bookModel.getTitle()
						+ "\nАвтор:\n" + bookModel.getInfo();
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

	@Override
	protected void onDestroy() {
		super.onDestroy();

		bookDBHelper.close();
	}
}