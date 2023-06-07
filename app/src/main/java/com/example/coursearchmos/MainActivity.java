package com.example.coursearchmos;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.coursearchmos.DataBase.BookDBAdapter;
import com.example.coursearchmos.DataBase.UserDBAdapter;
import com.example.coursearchmos.model.UserModel;
import com.example.coursearchmos.ui.library.LibraryFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.coursearchmos.databinding.ActivityMainBinding;
import com.example.coursearchmos.ui.notes.NotesFragment;
import com.example.coursearchmos.ui.reader.ReaderFragment;
import com.example.coursearchmos.ui.setting.SettingFragment;

public class MainActivity extends AppCompatActivity implements MainFragmentListener {
	public static final String SELECTED_FRAGMENT = "SELECTED_FRAGMENT";
	public static final String FLAG_IS_CHILD = "IS_CHILD";
	private BookDBAdapter bookDBHelper;
	private ActivityMainBinding binding;
	private boolean userIsChild;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		binding = ActivityMainBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		bookDBHelper = new BookDBAdapter(MainActivity.this);

		binding.navView.setOnItemSelectedListener((item) ->{
			switch (item.getItemId()) {
				case R.id.navigation_setting:
					replaceFragment(new SettingFragment());
					break;
				case R.id.navigation_reader:
					replaceFragment(new ReaderFragment());
					break;
				case R.id.navigation_library:
					replaceFragment(LibraryFragment.newInstance(userIsChild));
					break;
				case R.id.navigation_notes:
					replaceFragment(new NotesFragment());
					break;
			}
			return true;
		});

		binding.navView.getMenu().setGroupCheckable(0, false, true);
		Bundle args = getIntent().getExtras();
		if (args.containsKey(Authorization.USER_ID)) {
			userIsChild = args.getBoolean(Authorization.USER_ID);
		}
		if (args.containsKey(MainActivity.SELECTED_FRAGMENT))
			selectFragment(args.getString(MainActivity.SELECTED_FRAGMENT));
		else
			replaceFragment(new ReaderFragment());
	}

	public void openFile() {
		Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("application/pdf");

		startActivityForResult(intent, LibraryFragment.PICK_PDF_FILE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
		super.onActivityResult(requestCode, resultCode, resultData);
		if (requestCode == LibraryFragment.PICK_PDF_FILE
				&& resultCode == Activity.RESULT_OK) {
			Uri uri;
			if (resultData != null) {
				uri = resultData.getData();
				bookDBHelper.addOne(uri);
			}
		}
	}

	@Override
	public void addNote() {
		Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
		startActivity(intent);
	}

	private void selectFragment(String selectFrg) {
		switch (selectFrg) {
			case AddNoteActivity.IDENTIFY:
			case NoteActivity.IDENTIFY:
				replaceFragment(new NotesFragment());
				break;
			case BookActivity.IDENTIFY:
				replaceFragment(new ReaderFragment());
				break;
		}
	}

	public void replaceFragment(Fragment fragment) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.fragment_layout, fragment);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}
}