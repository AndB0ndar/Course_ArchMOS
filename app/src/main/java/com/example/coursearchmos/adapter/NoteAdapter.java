package com.example.coursearchmos.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursearchmos.R;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteVH> {
	List<String> notes;

	public NoteAdapter(List<String> notes) {
		this.notes = notes;
	}

	@NonNull
	@Override
	public NoteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.node, parent, false);
		return new NoteVH(view).LinkAdapter(this);
	}

	@Override
	public void onBindViewHolder(@NonNull NoteVH holder, int position) {
		 holder.textView.setText(notes.get(position));
	}

	@Override
	public int getItemCount() {
		return notes.size();
	}
}

class NoteVH extends RecyclerView.ViewHolder {
	TextView textView;
	private NoteAdapter adapter;

	public NoteVH(@NonNull View itemView) {
		super(itemView);
		textView = itemView.findViewById(R.id.text);
		itemView.findViewById(R.id.info).setOnClickListener(view -> {
			int pos = getAdapterPosition();
			adapter.notes.remove(pos);
			adapter.notifyItemRemoved(pos);
		});
	}

	public NoteVH LinkAdapter(NoteAdapter adapter) {
		this.adapter = adapter;
		return this;
	}
}
