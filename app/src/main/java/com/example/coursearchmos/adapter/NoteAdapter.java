package com.example.coursearchmos.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursearchmos.AddNoteActivity;
import com.example.coursearchmos.BookActivity;
import com.example.coursearchmos.NoteActivity;
import com.example.coursearchmos.R;
import com.example.coursearchmos.model.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
	Context context;
	List<Note> notes;

	public NoteAdapter(Context context, List<Note> notes) {
		this.context = context;
		this.notes = notes;
	}

	@NonNull
	@Override
	public BookAdapter.BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View bookItem = LayoutInflater.from(context).inflate(R.layout.note_item, parent, false);
		return new BookAdapter.BookViewHolder(bookItem);
	}

	@Override
	public void onBindViewHolder(@NonNull BookAdapter.BookViewHolder holder, int position) {
		holder.bookTitle.setText(notes.get(position).getTitle());
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, NoteActivity.class);
				context.startActivity(intent);
			}
		});
	}


	@Override
	public int getItemCount() {
		return notes.size();
	}

	private static final class NoteViewHolder extends RecyclerView.ViewHolder {
		TextView noteTitle;

		public NoteViewHolder(@NonNull View itemView) {
			super(itemView);

			noteTitle = itemView.findViewById(R.id.title);
		}
	}

}
