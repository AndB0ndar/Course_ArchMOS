package com.example.coursearchmos.ui.notes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursearchmos.DataBase.BookDBAdapter;
import com.example.coursearchmos.DataBase.NoteDBAdapter;
import com.example.coursearchmos.FragmentListener;
import com.example.coursearchmos.adapter.NoteAdapter;
import com.example.coursearchmos.databinding.FragmentNotesBinding;
import com.example.coursearchmos.model.NoteModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NotesFragment extends Fragment {
	private FragmentNotesBinding binding;
	private FragmentListener fragmentListener;

	protected NoteAdapter noteAdapter;
	private NoteDBAdapter noteDBHelper;
	private BookDBAdapter bookDBHelper;

	static List<NoteModel> notes = new ArrayList<>();
	HashMap<String, Integer> titles = new HashMap<>();

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		try {
			fragmentListener = (FragmentListener) context;
		} catch (ClassCastException e) {
			throw new ClassCastException(context.toString());
		}
	}

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		NotesViewModel notificationsViewModel =
				new ViewModelProvider(this).get(NotesViewModel.class);

		binding = FragmentNotesBinding.inflate(inflater, container, false);
		View root = binding.getRoot();

		noteDBHelper = new NoteDBAdapter(getContext());
		bookDBHelper = new BookDBAdapter(getContext());

		titles.putAll(bookDBHelper.getTitles());
		titles.put("Не выбрано", -1);
		notes = noteDBHelper.getAll();
		setNoteRecycler();
		setNoteSpinner();

		binding.btnAdd.setOnClickListener((v -> { fragmentListener.addNote();}));

		return root;
	}

	private void setNoteRecycler() {
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext()
				, RecyclerView.VERTICAL
				, false
		);
		binding.notesRecycler.setLayoutManager(layoutManager);
		noteAdapter = new NoteAdapter(getContext(), notes);
		binding.notesRecycler.setAdapter(noteAdapter);
	}

	private void setNoteSpinner() {
		ArrayAdapter<String> adapter = new ArrayAdapter(getContext()
				, android.R.layout.simple_spinner_item
				, titles.keySet().toArray());
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		binding.spinner2.setAdapter(adapter);
		AdapterView.OnItemSelectedListener itemSelectedListener =
				new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent, View view
							, int position, long id) {
						String item = (String)parent.getItemAtPosition(position);
						int idBook = titles.get(item);
						if (idBook == -1) {
							notes = noteDBHelper.getAll();
						} else {
							notes = noteDBHelper.getAllByBook(idBook);
						}
						noteAdapter.setNotes(notes);
					}
					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					}
				};
		binding.spinner2.setOnItemSelectedListener(itemSelectedListener);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;

		bookDBHelper.close();
		noteDBHelper.close();
	}
}