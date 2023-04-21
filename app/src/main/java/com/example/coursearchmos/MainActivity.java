package com.example.coursearchmos;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.coursearchmos.DataBase.BookDBHelper;
import com.example.coursearchmos.model.BookModel;
import com.example.coursearchmos.ui.library.LibraryFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.coursearchmos.databinding.ActivityMainBinding;
import com.example.coursearchmos.ui.notes.NotesFragment;
import com.example.coursearchmos.ui.reader.ReaderFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity implements FragmentListener {
	public static final String SELECTED_FRAGMENT = "SELECTED_FRAGMENT";
	private BookDBHelper bookDBHelper;
	private ActivityMainBinding binding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		binding = ActivityMainBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		bookDBHelper = new BookDBHelper(MainActivity.this);

		AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
				R.id.navigation_setting, R.id.navigation_reader, R.id.navigation_library, R.id.navigation_notes)
				.build();
		NavController navController = Navigation.findNavController(MainActivity.this
				, R.id.nav_host_fragment_activity_main);
		NavigationUI.setupWithNavController(binding.navView, navController);

		Bundle args = getIntent().getExtras();
		if (args != null) {
			selectFragment(args.getString(MainActivity.SELECTED_FRAGMENT));
		}
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
		Fragment fragment;
		switch (selectFrg) {
			case AddNoteActivity.IDENTIFY:
			case NoteActivity.IDENTIFY:
				fragment = new NotesFragment();
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.nav_host_fragment_activity_main, fragment)
						.addToBackStack(null)
						.commit();
				break;
			case BookActivity.IDENTIFY:
				fragment = new ReaderFragment();
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.nav_host_fragment_activity_main, fragment)
						.addToBackStack(null)
						.commit();
				break;
		}
	}
}