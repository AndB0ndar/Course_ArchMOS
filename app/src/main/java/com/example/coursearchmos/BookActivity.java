package com.example.coursearchmos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import com.example.coursearchmos.DataBase.BookDBHelper;
import com.example.coursearchmos.databinding.ActivityBookBinding;
import com.example.coursearchmos.model.BookModel;

import java.io.File;
import java.io.IOException;

public class BookActivity extends AppCompatActivity {
	private ActivityBookBinding binding;
	private BookModel bookModel;
	private BookDBHelper bookDBHelper;

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

		bookDBHelper = new BookDBHelper(BookActivity.this);

		Bundle args = getIntent().getExtras();
		bookModel = bookDBHelper.getById(args.getInt(BookModel.class.getCanonicalName()));

		path = bookModel.getPath();
		currentPage = bookModel.getLastCurPage();

		// устанавливаем слушатели на кнопки
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

		binding.back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), MainActivity.class);
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

	@Override
	public void onStart() {
		super.onStart();
		try {
			openPdfRenderer();
			displayPage(currentPage);
		} catch (Exception e) {
			Toast.makeText(this, "PDF-файл защищен паролем.", Toast.LENGTH_SHORT).show();
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
		// закрываем текущую страницу
		if (curPage != null)
			curPage.close();
		// открываем нужную страницу
		curPage = pdfRenderer.openPage(index);
		// определяем размеры Bitmap
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
		// отображаем результат рендера
		binding.imgView.setImageBitmap(bitmap);
		// проверяем, нужно ли делать кнопки недоступными
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
	protected void onResume() {
		super.onResume();
		timeStart = System.currentTimeMillis();
	}

	@Override
	protected void onPause() {
		super.onPause();
		int time = bookModel.getTime() + (int) ((System.currentTimeMillis() - timeStart) / 1000);
		bookModel.setTime(time);
	}

	@Override public void onStop() {
		super.onStop();
		try {
			closePdfRenderer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		bookModel.setLastCurPage(curPage.getIndex());
		bookDBHelper.updateOne(bookModel);
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