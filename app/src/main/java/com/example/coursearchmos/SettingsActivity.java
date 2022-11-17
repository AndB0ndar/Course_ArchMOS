package com.example.coursearchmos;

import static java.lang.Math.round;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.SeekBar;

import com.example.coursearchmos.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {
    private ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
                } else {
                    SetBrightness(context, progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        binding.btnReader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartReaderActivity(v);
            }
        });
        binding.btnLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartLibraryActivity(v);
            }
        });
        binding.btnNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartNotesActivity(v);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.brightnessSeekbar.setProgress(GetBrightness(this));
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private boolean ExistWriteSettingsPermission(Context context) {
        // Get the result from below code.
        return Settings.System.canWrite(context);
    }

    // Start can modify system settings panel to let user change the write
    // settings permission.
    @RequiresApi(api = Build.VERSION_CODES.M)
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

    public void StartLibraryActivity(View view) {
        Intent intent = new Intent(this, LibraryActivity.class);
        startActivity(intent);
    }
    public void StartReaderActivity(View view) {
        Intent intent = new Intent(this, ReaderActivity.class);
        startActivity(intent);
    }
    public void StartNotesActivity(View view) {
        Intent intent = new Intent(this, NotesActivity.class);
        startActivity(intent);
    }
}