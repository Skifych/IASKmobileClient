package com.itwell.iaskmobileclient;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class IASKmobileSettings extends PreferenceActivity 
{
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    addPreferencesFromResource(R.xml.iasksettings);
	}
}
