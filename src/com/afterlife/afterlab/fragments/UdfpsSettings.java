/*
 * Copyright (C) 2022 crDroid Android Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.afterlife.afterlab.fragments;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;

import androidx.preference.ListPreference;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreference;

import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.util.afterlife.AfterlifeUtils;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexable;

@SearchIndexable(forTarget = SearchIndexable.ALL & ~SearchIndexable.ARC)
public class UdfpsSettings extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    private static final String UDFPS_CUSTOMIZATION = "udfps_customization";
    private static final String KEY_UDFPS_ICONS = "udfps_icon_picker";

    private Preference mUdfpsCustomization;
    private Preference mUdfpsIcons;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.udfps_settings);

	final PreferenceScreen prefSet = getPreferenceScreen();
	Resources resources = getResources();
	final boolean udfpsResPkgInstalled = AfterlifeUtils.isPackageInstalled(getContext(),
        	"com.afterlife.udfps.resources");
	mUdfpsCustomization = findPreference(UDFPS_CUSTOMIZATION);
	mUdfpsIcons = findPreference(KEY_UDFPS_ICONS); // Change cast to findPreference
	if (mUdfpsCustomization != null && !udfpsResPkgInstalled) {
	    prefSet.removePreference(mUdfpsCustomization);
	    if (mUdfpsIcons != null) { // Check if mUdfpsIcons is not null before removing
            	prefSet.removePreference(mUdfpsIcons);
            }
    	}
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        ContentResolver resolver = getActivity().getContentResolver();
        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.AFTERLIFE;
    }

    /**
     * For Search.
     */
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.udfps_settings);
}
