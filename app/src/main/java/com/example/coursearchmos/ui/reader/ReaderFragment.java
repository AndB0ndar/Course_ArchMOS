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
		binding = FragmentReaderBinding.inflate(inflater, container, false);
		View root = binding.getRoot();

		BookDBHelper bookDBHelper = new BookDBHelper(getContext());
		if (!bookDBHelper.isEmpty()) {
			book = bookDBHelper.getLast();
			setBookRecycler();
			binding.countPages.setMax(book.getPageCount());
		}
		bookDBHelper.close();
		setNoteRecycler();

		return root;
	}

	private void setBookRecycler() {
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
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext()
				, RecyclerView.VERTICAL
				, false);
		binding.notesRecycler.setLayoutManager(layoutManager);
		NoteDBHelper noteDBHelper = new NoteDBHelper(getContext());
		if (!noteDBHelper.isEmpty()) {
			List<NoteModel> notes = noteDBHelper.getAllByBook(book.getId());
			if (!notes.isEmpty()) {
				noteAdapter = new NoteAdapter(getContext(), notes);
				binding.notesRecycler.setAdapter(noteAdapter);
			}
		}
		noteDBHelper.close();
	}

	@Override
	public void onResume() {
		super.onResume();
		binding.countPages.setProgress(book.getLastCurPage());
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}
}