package com.example.coursearchmos.ui.authorization;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.coursearchmos.DataBase.UserDBAdapter;
import com.example.coursearchmos.LoginFragmentListener;
import com.example.coursearchmos.R;
import com.example.coursearchmos.databinding.FragmentLoginBinding;
import com.example.coursearchmos.model.UserModel;

public class LoginFragment extends Fragment {
	private FragmentLoginBinding binding;
	private UserDBAdapter userDBAdapter;

	private LoginFragmentListener fragmentListener;

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
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		binding = FragmentLoginBinding.inflate(inflater, container, false);
		userDBAdapter = new UserDBAdapter(getContext());

		binding.register.setOnClickListener(v -> {
			RegisterFragment fragment = new RegisterFragment();
			getParentFragmentManager().beginTransaction()
					.replace(R.id.fragment_layout, fragment, null)
					.setReorderingAllowed(true)
					.addToBackStack("registration")
					.commit();
		});

		binding.enter.setOnClickListener(v -> {
			String login = binding.editTextLogin.getText().toString();
			String password = binding.editTextPassword.getText().toString();

			UserModel user = userDBAdapter.logIn(login, password);
			if (user != null) {
				getParentFragmentManager().popBackStack( // Clear stack
						null,
						FragmentManager.POP_BACK_STACK_INCLUSIVE
				);
				fragmentListener.cool(user.getChild());
			} else {
				binding.errorMessage.setText(R.string.incorrect_data);
				binding.errorMessage.setVisibility(View.VISIBLE);
			}
		});

		return binding.getRoot();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
		userDBAdapter.close();
	}
}