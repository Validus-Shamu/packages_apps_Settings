
package com.android.settings.validus;

import com.android.internal.logging.MetricsLogger;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.preference.ListPreference;
import android.util.Log;
import android.util.DisplayMetrics;

import com.android.settings.util.Helpers;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.dashboard.DashboardContainerView;

import java.util.List;

public class DayNightSettings extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {
    private static final String TAG = "DayNightSettings";
    
    private static final String DASHBOARD_COLUMNS = "dashboard_columns";

				// DayNight
    private static final String KEY_NIGHT_MODE = "night_mode";

    private ListPreference mNightModePreference;
    private ListPreference mDashboardColumns;

    @Override
    protected int getMetricsCategory() {
        return MetricsLogger.APPLICATION;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.daynight);

        mDashboardColumns = (ListPreference) findPreference(DASHBOARD_COLUMNS);
        mDashboardColumns.setValue(String.valueOf(Settings.System.getInt(
                getContentResolver(), Settings.System.DASHBOARD_COLUMNS, DashboardContainerView.mDashboardValue)));
        mDashboardColumns.setSummary(mDashboardColumns.getEntry());
        mDashboardColumns.setOnPreferenceChangeListener(this);

        mNightModePreference = (ListPreference) findPreference(KEY_NIGHT_MODE);
        if (mNightModePreference != null) {
            final UiModeManager uiManager = (UiModeManager) getSystemService(
                    Context.UI_MODE_SERVICE);
            final int currentNightMode = uiManager.getNightMode();
            mNightModePreference.setValue(String.valueOf(currentNightMode));
            mNightModePreference.setOnPreferenceChangeListener(this);
    			 }
     }
     
     @Override
     public boolean onPreferenceChange(Preference preference, Object objValue) {
         final String key = preference.getKey();
         if (preference == mDashboardColumns) {
            Settings.System.putInt(getContentResolver(), Settings.System.DASHBOARD_COLUMNS,
                    Integer.valueOf((String) objValue));
            mDashboardColumns.setValue(String.valueOf(objValue));
            mDashboardColumns.setSummary(mDashboardColumns.getEntry());
            return true;
         }
         if (preference == mNightModePreference) {
             try {
                 final int value = Integer.parseInt((String) objValue);
                 final UiModeManager uiManager = (UiModeManager) getSystemService(
                         Context.UI_MODE_SERVICE);
                 uiManager.setNightMode(value);
                 Helpers.restartSystemUI();
             } catch (NumberFormatException e) {
                 Log.e(TAG, "could not persist night mode setting", e);
             }
         }
         return true; 
       }
  }
