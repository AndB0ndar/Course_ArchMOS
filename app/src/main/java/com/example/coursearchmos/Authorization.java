package com.example.coursearchmos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.coursearchmos.databinding.ActivityAuthorizationBinding;
import com.example.coursearchmos.ui.authorization.LoginFragment;


public class Authorization extends AppCompatActivity implements LoginFragmentListener{
	public static final String USER_ID = "com.example.coursearchmos.Authorization ID";
	private ActivityAuthorizationBinding binding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityAuthorizationBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		replaceFragment(new LoginFragment());
	}

	public void replaceFragment(Fragment fragment) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.fragment_layout, fragment);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}

	@Override
	public void cool(boolean f) {
		Intent intent = new Intent(Authorization.this, MainActivity.class);
		intent.putExtra(USER_ID, f);
		startActivity(intent);
	}
}