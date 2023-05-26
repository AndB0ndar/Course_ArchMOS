package com.example.coursearchmos.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursearchmos.BookActivity;
import com.example.coursearchmos.DataBase.BookDBAdapter;
import com.example.coursearchmos.R;
//import com.example.coursearchmos.model.Book;
import com.example.coursearchmos.model.BookModel;

import java.io.File;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
	private static final int HEIGHT = 399;
	private static final double RATIO = 297.0 / 210.0;
	private final Context context;
	private List<BookModel> books;
	private boolean fg_remove;

	public BookAdapter(Context context, List<BookModel> books) {
		this.context = context;
		this.books = books;
		this.fg_remove = false;
	}

	@NonNull
	@Override
	public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View bookItem = LayoutInflater.from(context).inflate(R.layout.book_item
				, parent
				, false);
		return new BookViewHolder(bookItem);
	}

	@Override
	public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
		holder.bookImage.setImageBitmap(getImgBitmap(books.get(position).getPath()));
		holder.bookTitle.setText(books.get(position).getTitle());

		holder.itemView.setOnClickListener((v) -> {
			if (!fg_remove) {
				Intent intent = new Intent(context, BookActivity.class);
				intent.putExtra(BookActivity.BOOK_ID
						, books.get(holder.getAdapterPosition()).getId()
				);
				context.startActivity(intent);
			} else {
				BookDBAdapter bookDBHelper = new BookDBAdapter(v.getContext());
				bookDBHelper.deleteOne(books.get(position));
				books.remove(position);
				notifyDataSetChanged();
			}
		});
	}

	@Override
	public int getItemCount() {
		return books.size();
	}

	public static final class BookViewHolder extends RecyclerView.ViewHolder {
		ImageView bookImage;
		TextView bookTitle;

		public BookViewHolder(@NonNull View itemView) {
			super(itemView);
			bookImage = itemView.findViewById(R.id.image);
			bookTitle = itemView.findViewById(R.id.title);
		}
	}

	private Bitmap getImgBitmap(String path) {
		File file = new File(path);
		try {
			ParcelFileDescriptor descriptor = ParcelFileDescriptor.open(file
					, ParcelFileDescriptor.MODE_READ_ONLY);
			PdfRenderer pdfRenderer = new PdfRenderer(descriptor);

			PdfRenderer.Page curPage = pdfRenderer.openPage(0);
			Bitmap bitmap = Bitmap.createBitmap((int) (HEIGHT / RATIO), HEIGHT
					, Bitmap.Config.ARGB_8888);
			curPage.render(bitmap, null, null
					, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

			curPage.close();
			pdfRenderer.close();
			if (descriptor != null)
				descriptor.close();

			return bitmap;
		} catch (Exception e) {
			Log.d("BookAdapter", "Error open pdf document");
		}
		return null;
	}

	public void setBooks(List<BookModel> books) {
		this.books = books;
		notifyDataSetChanged();
	}

	public void setFgRemove(boolean fg_remove) {
		this.fg_remove = fg_remove;
	}
}
