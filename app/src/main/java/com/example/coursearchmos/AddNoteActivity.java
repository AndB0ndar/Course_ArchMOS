package com.example.coursearchmos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.coursearchmos.DataBase.BookDBHelper;
import com.example.coursearchmos.DataBase.NoteDBHelper;
import com.example.coursearchmos.databinding.ActivityAddNoteBinding;
import com.example.coursearchmos.model.NoteModel;

import java.util.HashMap;
import java.util.Map;

public class AddNoteActivity extends AppCompatActivity {
	public static final String IDENTIFY = "com.example.coursearchmos.AddNoteActivity";
	private ActivityAddNoteBinding binding;
	private NoteDBHelper noteDBHelper;
	private BookDBHelper bookDBHelper;
	private int idBook = -1;
	private Map<String, Integer> titles;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityAddNoteBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		noteDBHelper = new NoteDBHelper(AddNoteActivity.this);
		bookDBHelper = new BookDBHelper(AddNoteActivity.this);

		binding.saveButton.setOnClickListener((v -> saveAndBack()));

		titles = new HashMap<>();
		titles.put("", -1);
		titles.putAll(bookDBHelper.getTitles());

		ArrayAdapter<String> adapter = new ArrayAdapter(AddNoteActivity.this
				, android.R.layout.simple_spinner_item, titles.keySet().toArray());
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		binding.spinnerBooks.setAdapter(adapter);

		AdapterView.OnItemSelectedListener itemSelectedListener = new
				AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent, View view
							, int position, long id) {
						String item = (String)parent.getItemAtPosition(position);
						idBook = titles.get(item);
					}
					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					}
				};
		binding.spinnerBooks.setOnItemSelectedListener(itemSelectedListener);

		Bundle args = getIntent().getExtras();
		if (!args.isEmpty()) {
			if (args.containsKey(BookActivity.class.getCanonicalName())) {
				String title = args.getString(BookActivity.class.getCanonicalName());
				idBook = titles.get(title);
				int ind = findTitleIndex(title);
				binding.spinnerBooks.setSelection(ind);
			}
		}
	}

	private void saveAndBack() {
		Log.d("BookId", String.valueOf(idBook));
		NoteModel noteModel = new NoteModel(-1
				, binding.title.getText().toString().trim()
				, binding.text.getText().toString()
				, idBook);
		noteDBHelper.addOne(noteModel);

		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra(MainActivity.SELECTED_FRAGMENT, IDENTIFY);
		startActivity(intent);
	}

	private int findTitleIndex(String target)	{
		String[] lst = titles.keySet().toArray(new String[0]);
		for (int i = 0; i < lst.length; i++) {
			if (lst[i].equals(target)) {
				return i;
			}
		}
		return 0;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		noteDBHelper.close();
	}
}