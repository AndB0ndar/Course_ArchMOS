package com.example.coursearchmos.ui.reader;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursearchmos.DataBase.BookDBHelper;
import com.example.coursearchmos.DataBase.NoteDBHelper;
import com.example.coursearchmos.adapter.BookAdapter;
import com.example.coursearchmos.adapter.NoteAdapter;
import com.example.coursearchmos.databinding.FragmentReaderBinding;
import com.example.coursearchmos.model.BookModel;
import com.example.coursearchmos.model.NoteModel;

import java.util.ArrayList;
import java.util.List;

public class ReaderFragment extends Fragment {
	private FragmentReaderBinding binding;

	protected BookAdapter bookAdapter;
	protected NoteAdapter noteAdapter;
	static private BookModel book = null;

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		ReaderViewModel dashboardViewModel =
				new ViewModelProvider(this).get(ReaderViewModel.class);

		binding = FragmentReaderBinding.inflate(inflater, container, false);
		View root = binding.getRoot();

		BookDBHelper bookDBHelper = new BookDBHelper(getContext());
		if (!bookDBHelper.isEmpty()) {
			book = bookDBHelper.getLast();
			SetBookRecycler();
		}
		setNoteRecycler();

		return root;
	}

	private void SetBookRecycler() {
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext()
				, RecyclerView.HORIZONTAL
				, false);
		binding.readerRecycler.setLayoutManager(layoutManager);
		List<BookModel> books = new ArrayList<>();
		books.add(book);
		bookAdapter = new BookAdapter(getContext(), books);
		binding.readerRecycler.setAdapter(bookAdapter);
	}


	private void setNoteRecycler() {
		NoteDBHelper noteDBHelper = new NoteDBHelper(getContext());
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext()
				, RecyclerView.VERTICAL
				, false);
		binding.notesRecycler.setLayoutManager(layoutManager);
		List<NoteModel> notes = noteDBHelper.getAllByBook(0);
		noteAdapter = new NoteAdapter(getContext(), notes);
		binding.notesRecycler.setAdapter(noteAdapter);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}
}