package com.muhammad_adi_yusuf.moviecatalogue.view.settingPage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;

import androidx.preference.CheckBoxPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

import com.muhammad_adi_yusuf.moviecatalogue.R;
import com.muhammad_adi_yusuf.moviecatalogue.view.helper.ReminderMovieCatalogue;

import java.util.ArrayList;
import java.util.Objects;

public class SettingFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener {

    private String daily, movieReminder, src_movie, language;
    private SwitchPreference dailySwitch, movieReleaseSwitch;
    private CheckBoxPreference movieCheckBox, seriesCheckBox;
    private ReminderMovieCatalogue reminder = new ReminderMovieCatalogue();

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);

        initialize();
        checkToRadio();
        shareCheckBoxValue();

    }

    private void initialize() {
        daily = getResources().getString(R.string.key_day_reminder);
        movieReminder = getResources().getString(R.string.key_movie_reminder);
        src_movie = getResources().getString(R.string.key_movie);
        String src_tv = getResources().getString(R.string.key_series);
        language = getResources().getString(R.string.key_language);

        dailySwitch = findPreference(daily);
        movieReleaseSwitch = findPreference(movieReminder);
        movieCheckBox = findPreference(src_movie);
        seriesCheckBox = findPreference(src_tv);
        Objects.requireNonNull(findPreference(language)).setOnPreferenceClickListener(this);
    }

    private void checkToRadio() {
        final ArrayList<CheckBoxPreference> alViewMode = new ArrayList<>();

        Preference.OnPreferenceClickListener listener = new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                for (CheckBoxPreference cbp : alViewMode) {
                    if (!cbp.getKey().equals(preference.getKey()) && cbp.isChecked()) {
                        cbp.setChecked(false);
                    } else if (cbp.getKey().equals(preference.getKey()) && !cbp.isChecked()) {
                        cbp.setChecked(true);
                    }
                }
                return false;
            }
        };

        movieCheckBox.setOnPreferenceClickListener(listener);
        seriesCheckBox.setOnPreferenceClickListener(listener);

        alViewMode.add(movieCheckBox);
        alViewMode.add(seriesCheckBox);
    }

    private void shareCheckBoxValue() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(getContext()));
        SharedPreferences.Editor editor = preferences.edit();
        if (movieCheckBox.isChecked()) {
            editor.putBoolean(src_movie, true);
        } else {
            editor.putBoolean(src_movie, false);
        }
        editor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(daily)) {
            boolean state = sharedPreferences.getBoolean(daily, false);
            dailySwitch.setChecked(state);
            if (state) {
                String reminderTime = "07:00";
                reminder.setReminder(getActivity(), daily, reminderTime, getString(R.string.title_daily_reminder), getString(R.string.description_daily_reminder));
            } else {
                reminder.unReminder(getContext(), daily);
            }
        }

        if (key.equals(movieReminder)) {
            boolean state = sharedPreferences.getBoolean(movieReminder, false);
            movieReleaseSwitch.setChecked(state);
            if (state) {
                String reminderTime = "08:00";
                reminder.setReminder(getActivity(), movieReminder, reminderTime, getString(R.string.title_release_reminder), getString(R.string.description_release_reminder));
            } else {
                reminder.unReminder(getContext(), movieReminder);
            }
        }
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();

        if (key.equals(language)) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
            Objects.requireNonNull(getActivity()).finish();

            return true;
        }
        return false;
    }

}
