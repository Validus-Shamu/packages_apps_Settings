
package com.android.settings.validus;

import android.os.Bundle;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class SystemSettings extends SettingsPreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.thevoid_settings);
    }
}
