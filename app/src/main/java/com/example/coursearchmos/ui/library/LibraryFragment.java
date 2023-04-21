package com.example.coursearchmos.ui.library;

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

import com.example.coursearchmos.DataBase.BookDBHelper;
import com.example.coursearchmos.FragmentListener;
import com.example.coursearchmos.adapter.BookAdapter;
import com.example.coursearchmos.databinding.FragmentLibraryBinding;
import com.example.coursearchmos.model.BookModel;

import java.util.ArrayList;
import java.util.List;

public class LibraryFragment extends Fragment {
	public static final int PICK_PDF_FILE = 2;
	private FragmentListener fragmentListener;

	protected BookAdapter bookAdapter;
	private BookDBHelper bookDBHelper;
	static List<BookModel> books = new ArrayList<>();

	private FragmentLibraryBinding binding;

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
		LibraryViewModel libraryViewModel =
				new ViewModelProvider(this).get(LibraryViewModel.class);

		binding = FragmentLibraryBinding.inflate(inflater, container, false);
		View root = binding.getRoot();

		binding.btnAdd.setOnClickListener((v -> { fragmentListener.openFile(); }));

		bookDBHelper = new BookDBHelper(getContext());
		if (!bookDBHelper.isEmpty()) {
			books = bookDBHelper.getAll();
			SetBookRecycler();
		}

		binding.btnRemove.setOnCheckedChangeListener((buttonView, isChecked) -> {
				bookAdapter.setFgRemove(isChecked);
		});

//		libraryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
		return root;
	}

	private void SetBookRecycler() {
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext()
				, RecyclerView.VERTICAL
				, false);
		binding.libraryRecycler.setLayoutManager(layoutManager);

		bookAdapter = new BookAdapter(getContext(), books);
		binding.libraryRecycler.setAdapter(bookAdapter);
	}

	@Override
	public void onResume() {
		super.onResume();

		books = bookDBHelper.getAll();
		SetBookRecycler();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;

		bookDBHelper.close();
	}
}