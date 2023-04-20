package com.example.coursearchmos.ui.notes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursearchmos.DataBase.NoteDBHelper;
import com.example.coursearchmos.FragmentListener;
import com.example.coursearchmos.adapter.NoteAdapter;
import com.example.coursearchmos.databinding.FragmentNotesBinding;
import com.example.coursearchmos.model.NoteModel;

import java.util.ArrayList;
import java.util.List;

public class NotesFragment extends Fragment {
	private FragmentNotesBinding binding;
	private FragmentListener fragmentListener;

	protected NoteAdapter noteAdapter;
	private NoteDBHelper noteDBHelper;

	static List<NoteModel> notes = new ArrayList<>();

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

		noteDBHelper = new NoteDBHelper(getContext());
		notes = noteDBHelper.getAll();

		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext()
				, RecyclerView.VERTICAL
				, false
		);
		binding.notesRecycler.setLayoutManager(layoutManager);
		noteAdapter = new NoteAdapter(getContext(), notes);
		binding.notesRecycler.setAdapter(noteAdapter);

		binding.btnAdd.setOnClickListener((v -> { fragmentListener.addNote();}));

//		notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
		return root;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;

		noteDBHelper.close();
	}
}