package com.example.coursearchmos.ui.reader;

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
import com.example.coursearchmos.adapter.BookAdapter;
import com.example.coursearchmos.databinding.FragmentReaderBinding;
import com.example.coursearchmos.model.BookModel;

import java.util.ArrayList;
import java.util.List;

public class ReaderFragment extends Fragment {
	private FragmentReaderBinding binding;

	protected BookAdapter bookAdapter;
	static private BookModel book = null;
	private BookDBHelper bookDBHelper;

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		ReaderViewModel dashboardViewModel =
				new ViewModelProvider(this).get(ReaderViewModel.class);

		binding = FragmentReaderBinding.inflate(inflater, container, false);
		View root = binding.getRoot();

		bookDBHelper = new BookDBHelper(getContext());

//		Bundle args = getIntent().getExtras();
//		if (args != null) {
//			int id = args.getInt(BookModel.class.getCanonicalName());
//			book = bookDBHelper.getById(id);
//		}
		if (book != null) {
			SetBookRecycler(book);
		}

//		dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
		return root;
	}

	private void SetBookRecycler(BookModel book) {
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext()
				, RecyclerView.VERTICAL
				, false);
		binding.readerRecycler.setLayoutManager(layoutManager);

		List<BookModel> books = new ArrayList<>();
		books.add(book);
		bookAdapter = new BookAdapter(getContext(), books);
		binding.readerRecycler.setAdapter(bookAdapter);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}
}