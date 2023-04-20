package com.example.coursearchmos.ui.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.coursearchmos.databinding.FragmentSettingBinding;

public class SettingFragment extends Fragment {

	private FragmentSettingBinding binding;

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		SettingViewModel homeViewModel =
				new ViewModelProvider(this).get(SettingViewModel.class);

		binding = FragmentSettingBinding.inflate(inflater, container, false);
		View root = binding.getRoot();

		binding.brightnessSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				Context context = seekBar.getContext();

				// Check whether has the write settings permission or not.
				boolean settingsCanWrite = false;
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
					settingsCanWrite = ExistWriteSettingsPermission(context);
				}

				// If do not have then open the Can modify system settings panel.
				if (!settingsCanWrite) {
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
						changeWriteSettingsPermission(context);
					}
				}
				// TODO: make the change more proportional
				SetBrightness(context, progress);
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});

//		homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
		return root;
	}

	@RequiresApi(Build.VERSION_CODES.M)
	private boolean ExistWriteSettingsPermission(Context context) {
		// Get the result from below code.
		return Settings.System.canWrite(context);
	}

	// Start can modify system settings panel to let user change the write
	// settings permission.
	@RequiresApi(Build.VERSION_CODES.M)
	private void changeWriteSettingsPermission(Context context) {
		Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
		context.startActivity(intent);
	}

	// This function only take effect in real physical android device,
	// it can not take effect in android emulator.
	private void SetBrightness(Context context, int screenBrightnessValue) {
		// Change the screen brightness change mode to manual.
		Settings.System.putInt(
				context.getContentResolver(),
				Settings.System.SCREEN_BRIGHTNESS_MODE,
				Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
		);
		// Apply the screen brightness value to the system, this will change
		// the value in Settings ---> Display ---> Brightness level.
		// It will also change the screen brightness for the device.
		Settings.System.putInt(
				context.getContentResolver(),
				Settings.System.SCREEN_BRIGHTNESS, screenBrightnessValue
		);
	}

	private int GetBrightness(Context context) {
		return Settings.System.getInt(
				context.getContentResolver(),
				Settings.System.SCREEN_BRIGHTNESS,
				0
		);
	}

	@Override
	public void onResume() {
		super.onResume();
		binding.brightnessSeekbar.setProgress(GetBrightness(getContext()));
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}
}