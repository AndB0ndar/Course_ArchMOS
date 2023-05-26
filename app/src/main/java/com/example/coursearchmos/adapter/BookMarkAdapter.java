package com.example.coursearchmos.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursearchmos.BookActivity;
import com.example.coursearchmos.DataBase.BookMarksDBAdapter;
import com.example.coursearchmos.R;
import com.example.coursearchmos.model.BookMarkModel;

import java.util.List;

public class BookMarkAdapter extends RecyclerView.Adapter<BookMarkAdapter.BookMarkViewHolder> {
	private final Context context;
	private List<BookMarkModel> bookmarks;
	private boolean fg_remove;

	public BookMarkAdapter(Context context, List<BookMarkModel> bookmarks) {
		this.context = context;
		this.bookmarks = bookmarks;
		this.fg_remove = false;
	}

	@NonNull
	@Override
	public BookMarkAdapter.BookMarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View noteItem = LayoutInflater.from(context).inflate(R.layout.bookmark_item, parent, false);
		return new BookMarkAdapter.BookMarkViewHolder(noteItem);
	}

	@Override
	public void onBindViewHolder(BookMarkAdapter.BookMarkViewHolder holder, int position) {
		holder.bmTitle.setText(bookmarks.get(position).getTitle());
		holder.bmPage.setText(String.valueOf(bookmarks.get(position).getBookPage()));

		holder.itemView.setOnClickListener((v) -> {
			if (!fg_remove) {
				Intent intent = new Intent(context, BookActivity.class);
				intent.putExtra(BookActivity.BOOK_ID, bookmarks.get(position).getBookId());
				intent.putExtra(BookActivity.CURRENT_PAGE, bookmarks.get(position).getBookPage());
//				intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
//						| Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
				context.startActivity(intent);
			} else {
				BookMarksDBAdapter bookMarksDBAdapter = new BookMarksDBAdapter(v.getContext());
				bookMarksDBAdapter.deleteOne(bookmarks.get(position));
				bookmarks.remove(position);
				notifyDataSetChanged();
			}
		});
	}

	@Override
	public int getItemCount() {
		return bookmarks.size();
	}

	public static final class BookMarkViewHolder extends RecyclerView.ViewHolder {
		TextView bmTitle;
		TextView bmPage;

		public BookMarkViewHolder(@NonNull View itemView) {
			super(itemView);
			bmTitle = itemView.findViewById(R.id.title);
			bmPage = itemView.findViewById(R.id.page);
		}
	}

	public void setBookmarks(List<BookMarkModel> bookmarks) {
		this.bookmarks = bookmarks;
		notifyDataSetChanged();
	}

	public void setFgRemove(boolean fg_remove) {
		this.fg_remove = fg_remove;
	}
}

