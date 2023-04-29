package com.example.coursearchmos.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursearchmos.NoteActivity;
import com.example.coursearchmos.R;
import com.example.coursearchmos.model.NoteModel;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
	private Context context;
	private List<NoteModel> notes;

	public NoteAdapter(Context context, List<NoteModel> notes) {
		this.context = context;
		this.notes = notes;
	}

	@NonNull
	@Override
	public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View noteItem = LayoutInflater.from(context).inflate(R.layout.note_item, parent, false);
		return new NoteViewHolder(noteItem);
	}

	@Override
	public void onBindViewHolder(NoteViewHolder holder, int position) {
		holder.noteTitle.setText(notes.get(position).getTitle());
		holder.itemView.setOnClickListener((v) -> {
			Intent intent = new Intent(context, NoteActivity.class);
			intent.putExtra(NoteModel.class.getCanonicalName()
					, notes.get(holder.getAdapterPosition()).getId()
			);
			context.startActivity(intent);
		});
	}

	@Override
	public int getItemCount() {
		return notes.size();
	}

	public static final class NoteViewHolder extends RecyclerView.ViewHolder {
		TextView noteTitle;

		public NoteViewHolder(@NonNull View itemView) {
			super(itemView);
			noteTitle = itemView.findViewById(R.id.title);
		}
	}
}
