package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.CheckBoxPreference;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class PreferencesSharedFragment extends PreferenceFragmentCompat {

    private EditTextPreference theme_view;
    private CheckBoxPreference saveDarkTheme;
    public String mystring = "DARK";
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);

        saveDarkTheme = findPreference("theme");
        theme_view = findPreference("theme_view");

        sharedPreferences = getActivity().getSharedPreferences("theme", Context.MODE_PRIVATE);
        saveDarkTheme.setChecked(sharedPreferences.getBoolean(mystring, false));

        saveDarkTheme.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if ((boolean) newValue == true) {
                    theme_view.setText("DARK");
                } else {
                    theme_view.setText("not DARK");
                }
                return true;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(mystring, saveDarkTheme.isChecked());
        editor.apply();
    }
}