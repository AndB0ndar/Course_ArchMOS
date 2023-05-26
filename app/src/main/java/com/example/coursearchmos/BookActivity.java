package com.example.coursearchmos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.coursearchmos.DataBase.BookDBAdapter;
import com.example.coursearchmos.databinding.ActivityBookBinding;
import com.example.coursearchmos.model.BookModel;

import java.io.File;
import java.io.IOException;

public class BookActivity extends AppCompatActivity {
	public static final String IDENTIFY = "com.example.coursearchmos.BookActivity";
	public static final String BOOK_ID = "com.example.coursearchmos.BookActivity_BOOK_ID";
	public static final String CURRENT_PAGE = "com.example.coursearchmos.BookActivity_CURRENT_PAGE";
	private ActivityBookBinding binding;
	private BookModel book;
	private BookDBAdapter bookDBHelper;

	private String path;
	private int currentPage = 0;

	private PdfRenderer pdfRenderer;
	private PdfRenderer.Page curPage;
	private ParcelFileDescriptor descriptor;
	private float currentZoomLevel = 7;

	private long timeStart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityBookBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		bookDBHelper = new BookDBAdapter(BookActivity.this);

		Bundle args = getIntent().getExtras();
		if (args != null) {
			int id = args.getInt(BookActivity.BOOK_ID);
			book = bookDBHelper.getById(id);
			path = book.getPath();
			currentPage = (args.containsKey(CURRENT_PAGE))
					? args.getInt(CURRENT_PAGE) : book.getLastCurPage();
		}

		binding.btnPrevious.setOnClickListener((v -> {
			int index = curPage.getIndex() - 1;
			displayPage(index);
		}));
		binding.btnNext.setOnClickListener((v -> {
			int index = curPage.getIndex() + 1;
			displayPage(index);
		}));
		binding.zoomin.setOnClickListener((v -> {
			--currentZoomLevel;
			displayPage(curPage.getIndex());
		}));
		binding.zoomout.setOnClickListener((v -> {
			++currentZoomLevel;
			displayPage(curPage.getIndex());
		}));

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.book_menu, menu);
		getSupportActionBar().setTitle(book.getTitle());
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		int id = item.getItemId();
		Intent intent;
		switch (id){
			case android.R.id.home:
				intent = new Intent(BookActivity.this, MainActivity.class);
				intent.putExtra(MainActivity.SELECTED_FRAGMENT, IDENTIFY);
				startActivity(intent);
				return true;
			case R.id.info:
				String text = "Название:\n" + book.getTitle()
						+ "\nПурь:\n" + book.getPath();
				ShowInfo(text);
				return true;
			case R.id.add_note:
				intent = new Intent(BookActivity.this, AddNoteActivity.class);
				intent.putExtra(BookActivity.class.getCanonicalName(), book.getTitle());
				startActivity(intent);
				return true;
			case R.id.bookmarks:
				intent = new Intent(BookActivity.this, BookMarksActivity.class);
				intent.putExtra(BOOK_ID, book.getId());
				intent.putExtra(CURRENT_PAGE, currentPage);
				startActivity(intent);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onStart() {
		super.onStart();
		try {
			openPdfRenderer();
			displayPage(currentPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void openPdfRenderer() {
		File file = new File(path);
		descriptor = null;
		pdfRenderer = null;
		try {
			descriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
			pdfRenderer = new PdfRenderer(descriptor);
		} catch (Exception e) {
			Toast.makeText(this, "Ошибка", Toast.LENGTH_LONG).show();
		}
	}

	private void displayPage(int index) {
		if (pdfRenderer.getPageCount() <= index)
			return;
		if (curPage != null)
			curPage.close();
		curPage = pdfRenderer.openPage(index);
		int newWidth =
				(int) (getResources().getDisplayMetrics().widthPixels * curPage.getWidth() / 72
						* currentZoomLevel / 40);
		int newHeight =
				(int) (getResources().getDisplayMetrics().heightPixels * curPage.getHeight() / 72
						* currentZoomLevel / 64);
		Bitmap bitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

//		Matrix matrix = new Matrix();
//		float dpiAdjustedZoomLevel = currentZoomLevel * DisplayMetrics.DENSITY_MEDIUM
//				/ getResources().getDisplayMetrics().densityDpi;
//		matrix.setScale(dpiAdjustedZoomLevel, dpiAdjustedZoomLevel);
//		curPage.render(bitmap, null, matrix, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

		curPage.render(bitmap, null, null
				, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
		binding.imgView.setImageBitmap(bitmap);
		int pageCount = pdfRenderer.getPageCount();
		binding.btnPrevious.setEnabled(0 != index);
		binding.btnNext.setEnabled(index + 1 < pageCount);
		binding.zoomout.setEnabled(currentZoomLevel != 2);
		binding.zoomin.setEnabled(currentZoomLevel != 12);
	}

	private void ShowInfo(String text) {
		AlertDialog.Builder builder = new AlertDialog.Builder(BookActivity.this);
		builder.setTitle("Информация о книге")
				.setMessage(text)
				.setCancelable(true)
				.setPositiveButton("Хорошо", (dialog, which) -> dialog.cancel());
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	@Override
	protected void onResume() {
		super.onResume();
		timeStart = System.currentTimeMillis();
	}

	@Override
	protected void onPause() {
		super.onPause();
		int time = book.getTime() + (int) ((System.currentTimeMillis() - timeStart) / 1000);
		book.setTime(time);
		book.setLastCurPage(curPage.getIndex());
		bookDBHelper.updateOne(book);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			closePdfRenderer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		bookDBHelper.close();
	}

	private void closePdfRenderer() throws IOException {
		if (curPage != null)
			curPage.close();
		if (pdfRenderer != null)
			pdfRenderer.close();
		if (descriptor != null)
			descriptor.close();
	}
}