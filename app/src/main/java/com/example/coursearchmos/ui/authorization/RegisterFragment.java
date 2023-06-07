package com.example.coursearchmos.ui.authorization;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.coursearchmos.AddNoteActivity;
import com.example.coursearchmos.DataBase.UserDBAdapter;
import com.example.coursearchmos.LoginFragmentListener;
import com.example.coursearchmos.R;
import com.example.coursearchmos.databinding.FragmentRegisterBinding;
import com.example.coursearchmos.model.UserModel;

import java.util.HashMap;
import java.util.Map;

public class RegisterFragment extends Fragment {
	private FragmentRegisterBinding binding;

	private LoginFragmentListener fragmentListener;
	private UserDBAdapter userDBAdapter;
	private Map<String, Boolean> titles = new HashMap<>();

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		try {
			fragmentListener = (LoginFragmentListener) context;
		} catch (ClassCastException e) {
			throw new ClassCastException(context.toString());
		}
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		binding = FragmentRegisterBinding.inflate(inflater, container, false);
		userDBAdapter = new UserDBAdapter(getContext());

		titles.put("Взрослый", false);
		titles.put("Ребенок", true);
		setSpinner();

		binding.register.setOnClickListener(v -> {
			String login = binding.editTextLogin.getText().toString();
			String password = binding.editTextPassword.getText().toString();
			String repeat = binding.editTextRepeat.getText().toString();
			boolean isChild = titles.get(binding.spinner.getSelectedItem().toString());

			if (login.isEmpty() || password.isEmpty() || repeat.isEmpty()) {
				binding.errorMessage.setText(R.string.incorrect_data);
				binding.errorMessage.setVisibility(View.VISIBLE);
				return;
			}
			if (!password.equals(repeat)) {
				binding.errorMessage.setText(R.string.password_mismatch);
				binding.errorMessage.setVisibility(View.VISIBLE);
				return;
			}

			if (userDBAdapter.isExist(login)) {
				binding.errorMessage.setText(R.string.different_login);
				binding.errorMessage.setVisibility(View.VISIBLE);
				return;
			}

			userDBAdapter.addOne(login, password, isChild);
			fragmentListener.cool(isChild);
		});

		return binding.getRoot();
	}

	private void setSpinner() {
		ArrayAdapter<String> adapter = new ArrayAdapter(getContext()
				, android.R.layout.simple_spinner_item, titles.keySet().toArray());
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		binding.spinner.setAdapter(adapter);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
		userDBAdapter.close();
	}
}