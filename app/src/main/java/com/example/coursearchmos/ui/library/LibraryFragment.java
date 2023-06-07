package com.example.coursearchmos.ui.library;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.coursearchmos.DataBase.BookDBAdapter;
import com.example.coursearchmos.MainActivity;
import com.example.coursearchmos.MainFragmentListener;
import com.example.coursearchmos.adapter.BookAdapter;
import com.example.coursearchmos.databinding.FragmentLibraryBinding;
import com.example.coursearchmos.model.BookModel;

import java.util.ArrayList;
import java.util.List;

public class LibraryFragment extends Fragment {
	public static final int PICK_PDF_FILE = 2;
	public static final String FLAG_IS_CHILD = "com.example.coursearchmos.ui.library FLAG_IS_CHILD";
	private MainFragmentListener fragmentListener;

	protected BookAdapter bookAdapter;
	private BookDBAdapter bookDBHelper;
	static List<BookModel> books;

	private FragmentLibraryBinding binding;
	private boolean f_child;

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		try {
			fragmentListener = (MainFragmentListener) context;
		} catch (ClassCastException e) {
			throw new ClassCastException(context.toString());
		}
	}

	public static LibraryFragment newInstance(Boolean isChild) {
		LibraryFragment fragment = new LibraryFragment();
		Bundle args = new Bundle();
		args.putBoolean(FLAG_IS_CHILD, isChild);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null)
			f_child = getArguments().getBoolean(FLAG_IS_CHILD);
	}


	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		binding = FragmentLibraryBinding.inflate(inflater, container, false);
		View root = binding.getRoot();

		binding.btnAdd.setVisibility(f_child ? View.INVISIBLE : View.VISIBLE);
		binding.btnRemove.setVisibility(f_child ? View.INVISIBLE : View.VISIBLE);
		binding.btnAdd.setOnClickListener((v -> fragmentListener.openFile()));

		bookDBHelper = new BookDBAdapter(getContext());
		if (!bookDBHelper.isEmpty())
			books = bookDBHelper.getAll();
		else
			books = new ArrayList<>();
		setBookRecycler();

		binding.btnRemove.setOnCheckedChangeListener((buttonView, isChecked)
				-> bookAdapter.setFgRemove(isChecked));

		return root;
	}

	private void setBookRecycler() {
		bookAdapter = new BookAdapter(getContext(), books);
		GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
		binding.libraryRecycler.setLayoutManager(layoutManager);
		binding.libraryRecycler.setAdapter(bookAdapter);
	}

	@Override
	public void onResume() {
		super.onResume();

		List<BookModel> tmp_books = bookDBHelper.getAll();
		if (!books.equals(tmp_books))
			bookAdapter.setBooks(tmp_books);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
		bookDBHelper.close();
	}
}