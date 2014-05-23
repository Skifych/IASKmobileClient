package com.itwell.iaskmobileclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.AlertDialog;
import android.app.Dialog.*;

import org.apache.http.util.EntityUtils;

import com.google.gson.stream.JsonReader;













//import aaaJSONtest1.TryToTestDBclass;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;


public class SearchByUniqID extends ActionBarActivity {
	public static String proto = "http";
	public static String host = "192.168.27.7";
	public static int port = 8080;
	public static String slash = "://";
	public static String hostAddr = proto + slash + host + ":8080";
	public static String getPGidURL = "/iaskdbport-portlet/api/secure/jsonws/iaskpreporategroup/get-ias-kpreporate-group-by-id/id/";
	public static String getPrepIdURL = "/iaskdbport-portlet/api/secure/jsonws/iaskpreporate/get-ias-kpreporate-by-id/id/";
	public static String getASidURL = "/iaskdbport-portlet/api/secure/jsonws/iaskactivesubstance/get-ias-kactive-substance-by-id/id/";
	public static String getContainerIdURL = "/iaskdbport-portlet/api/secure/jsonws/iaskcontainer/get-ias-kcontainer-by-id/id/";
	public static String getCTidURL = "/iaskdbport-portlet/api/secure/jsonws/iaskcontainertype/get-ias-kcontainer-type-by-id/id/";
	public static String getCVidURL = "/iaskdbport-portlet/api/secure/jsonws/iaskcontainervolume/get-ias-kcontainer-volume-by-id/id/";
	public static String getCAidURL = "/iaskdbport-portlet/api/secure/jsonws/iaskcontragent/get-ias-kcontragents-by-id/id/";
	public static String getICidURL = "/iaskdbport-portlet/api/secure/jsonws/iaskimportercertificate/get-ias-kimporter-certificate-by-id/id/";
	public static String getConsigIdURL = "/iaskdbport-portlet/api/secure/jsonws/iaskconsigment/get-ias-kconsigment-by-id/id/";
	public static String userName = "username@itwell.com.ua";
	public static String userPassword = "userPassword";
	SharedPreferences sp;
	// 
	//hostAddr = hostAddr + ":" + "8080";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_by_uniq_id);
		//if ((port!=80)&&(port!=0)){
		//	;/	
		//}
		

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_by_uniq_id, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			showSettings();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_search_by_uniq_id, container, false);
			return rootView;
		}
	}
	
	public void showSettings()
	{
		Intent i = new Intent(this, IASKmobileSettings.class);
		startActivity(i);
	}
	
	public void onClickSearch(View v) {
		final EditText tfUniqID = (EditText) findViewById(R.id.tfUniqID);
		final TextView tvResultData = (TextView) findViewById(R.id.tvResultData);
		final TextView tvPgNum = (TextView) findViewById(R.id.tvPgNum);
		final TextView tvPgNumData = (TextView) findViewById(R.id.tvPgNumData);
		final TextView tvConsigNum = (TextView) findViewById(R.id.tvConsigNum);
		final TextView tvConsigNumData = (TextView) findViewById(R.id.tvConsigNumData);
		final TextView tvExporterNum = (TextView) findViewById(R.id.tvExporterNum);
		final TextView tvExportNumData = (TextView) findViewById(R.id.tvExportNumData);
		final TextView tvPrepName = (TextView) findViewById(R.id.tvPrepName);
		final TextView tvPrepNameData = (TextView) findViewById(R.id.tvPrepNameData);
		final TextView tvActiveSubs = (TextView) findViewById(R.id.tvActiveSubs);
		final TextView tvActiveSubsData = (TextView) findViewById(R.id.tvActiveSubsData);
		final TextView tvMakerPrep = (TextView) findViewById(R.id.tvMakerPrep);
		final TextView tvMakerPrepData = (TextView) findViewById(R.id.tvMakerPrepData);
		final TextView tvMakerAS = (TextView) findViewById(R.id.tvMakerAS);
		final TextView tvMakerASdata = (TextView) findViewById(R.id.tvMakerASdata);
		final TextView tvImporter = (TextView) findViewById(R.id.tvImporter);
		final TextView tvImporterData = (TextView) findViewById(R.id.tvImporterData);
		final TextView tvCertNum = (TextView) findViewById(R.id.tvCertNum);
		final TextView tvCertData = (TextView) findViewById(R.id.tvCertData);
		final TextView tvNationRec = (TextView) findViewById(R.id.tvNationRec);
		final TextView tvNatRecData = (TextView) findViewById(R.id.tvNationRecData);
		final TextView tvContainer = (TextView) findViewById(R.id.tvContainer);
		final TextView tvContainerData = (TextView) findViewById(R.id.tvContainerData);
		final TextView tvNumUnits = (TextView) findViewById(R.id.tvNumUnits);
		final TextView tvNumUnitsData = (TextView) findViewById(R.id.tvNumUnitsData);
		
		tvPgNum.setVisibility(View.INVISIBLE);
		tvPgNumData.setVisibility(View.INVISIBLE);
		tvConsigNum.setVisibility(View.INVISIBLE);
		tvConsigNumData.setVisibility(View.INVISIBLE);
		tvExporterNum.setVisibility(View.INVISIBLE);
		tvExportNumData.setVisibility(View.INVISIBLE);
		tvPrepName.setVisibility(View.INVISIBLE);
		tvPrepNameData.setVisibility(View.INVISIBLE);
		tvActiveSubs.setVisibility(View.INVISIBLE);
		tvActiveSubsData.setVisibility(View.INVISIBLE);
		tvMakerPrep.setVisibility(View.INVISIBLE);
		tvMakerPrepData.setVisibility(View.INVISIBLE);
		tvMakerAS.setVisibility(View.INVISIBLE);
		tvMakerASdata.setVisibility(View.INVISIBLE);
		tvImporter.setVisibility(View.INVISIBLE);
		tvImporterData.setVisibility(View.INVISIBLE);
		tvCertNum.setVisibility(View.INVISIBLE);
		tvCertData.setVisibility(View.INVISIBLE);
		tvNationRec.setVisibility(View.INVISIBLE);
		tvNatRecData.setVisibility(View.INVISIBLE);
		tvContainer.setVisibility(View.INVISIBLE);
		tvContainerData.setVisibility(View.INVISIBLE);
		tvNumUnits.setVisibility(View.INVISIBLE);
		tvNumUnitsData.setVisibility(View.INVISIBLE);
		if ((!tfUniqID.getText().toString().equals(""))&&(tfUniqID.getText().length() != 0)){
	    	    getDataUniqID(Integer.valueOf(tfUniqID.getText().toString()));
		}
		else {
			tvResultData.setText("Insert data");
		}
    }
	
	
	public void getDataUniqID(int urlID) {
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		proto = sp.getString("protoList", "http");
		host = sp.getString("hostAddr", "127.0.0.1");
		userName = sp.getString("userName", userName);
		userPassword = sp.getString("userPassword", userPassword);
		hostAddr = proto + slash + host + ":8080";
		IASKuniqID uniqID = new IASKuniqID();
		final TextView tvResultData = (TextView) findViewById(R.id.tvResultData);
		final TextView tvPgNum = (TextView) findViewById(R.id.tvPgNum);
		final TextView tvPgNumData = (TextView) findViewById(R.id.tvPgNumData);
		final TextView tvConsigNum = (TextView) findViewById(R.id.tvConsigNum);
		final TextView tvConsigNumData = (TextView) findViewById(R.id.tvConsigNumData);
		final TextView tvExporterNum = (TextView) findViewById(R.id.tvExporterNum);
		final TextView tvExportNumData = (TextView) findViewById(R.id.tvExportNumData);
		final TextView tvPrepName = (TextView) findViewById(R.id.tvPrepName);
		final TextView tvPrepNameData = (TextView) findViewById(R.id.tvPrepNameData);
		final TextView tvActiveSubs = (TextView) findViewById(R.id.tvActiveSubs);
		final TextView tvActiveSubsData = (TextView) findViewById(R.id.tvActiveSubsData);
		final TextView tvMakerPrep = (TextView) findViewById(R.id.tvMakerPrep);
		final TextView tvMakerPrepData = (TextView) findViewById(R.id.tvMakerPrepData);
		final TextView tvMakerAS = (TextView) findViewById(R.id.tvMakerAS);
		final TextView tvMakerASdata = (TextView) findViewById(R.id.tvMakerASdata);
		final TextView tvImporter = (TextView) findViewById(R.id.tvImporter);
		final TextView tvImporterData = (TextView) findViewById(R.id.tvImporterData);
		final TextView tvCertNum = (TextView) findViewById(R.id.tvCertNum);
		final TextView tvCertData = (TextView) findViewById(R.id.tvCertData);
		final TextView tvNationRec = (TextView) findViewById(R.id.tvNationRec);
		final TextView tvNatRecData = (TextView) findViewById(R.id.tvNationRecData);
		final TextView tvContainer = (TextView) findViewById(R.id.tvContainer);
		final TextView tvContainerData = (TextView) findViewById(R.id.tvContainerData);
		final TextView tvNumUnits = (TextView) findViewById(R.id.tvNumUnits);
		final TextView tvNumUnitsData = (TextView) findViewById(R.id.tvNumUnitsData);
		
		tvPgNum.setVisibility(View.INVISIBLE);
		tvPgNumData.setVisibility(View.INVISIBLE);
		tvConsigNum.setVisibility(View.INVISIBLE);
		tvConsigNumData.setVisibility(View.INVISIBLE);
		tvExporterNum.setVisibility(View.INVISIBLE);
		tvExportNumData.setVisibility(View.INVISIBLE);
		tvPrepName.setVisibility(View.INVISIBLE);
		tvPrepNameData.setVisibility(View.INVISIBLE);
		tvActiveSubs.setVisibility(View.INVISIBLE);
		tvActiveSubsData.setVisibility(View.INVISIBLE);
		tvMakerPrep.setVisibility(View.INVISIBLE);
		tvMakerPrepData.setVisibility(View.INVISIBLE);
		tvMakerAS.setVisibility(View.INVISIBLE);
		tvMakerASdata.setVisibility(View.INVISIBLE);
		tvImporter.setVisibility(View.INVISIBLE);
		tvImporterData.setVisibility(View.INVISIBLE);
		tvCertNum.setVisibility(View.INVISIBLE);
		tvCertData.setVisibility(View.INVISIBLE);
		tvNationRec.setVisibility(View.INVISIBLE);
		tvNatRecData.setVisibility(View.INVISIBLE);
		tvContainer.setVisibility(View.INVISIBLE);
		tvContainerData.setVisibility(View.INVISIBLE);
		tvNumUnits.setVisibility(View.INVISIBLE);
		tvNumUnitsData.setVisibility(View.INVISIBLE);

		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
				new UsernamePasswordCredentials(userName, userPassword));
		
		Log.d("uniqID", "uniqID.setHostAddr(hostAddr)");
		uniqID.setHostAddr(hostAddr);
		Log.d("uniqID", "uniqID.setHttpClient(barcodeSearch.getHttpClient())");
		uniqID.setHttpClient(httpClient);
		Log.d("uniqID", "uniqID.setPrepGroupID(Integer.valueOf(barcodeSearch.getBarcode().getPreporateGroupID().toString()))");
		uniqID.setPrepGroupID(urlID);
		Log.d("uniqID", "getAllData");
		uniqID.getAllData();
		Log.d("uniqID", "getAllData - completed");
		if ((uniqID.resultOK())&&(uniqID.getPgResult()!=null)){
			tvPgNum.setVisibility(View.VISIBLE);
			tvPgNumData.setVisibility(View.VISIBLE);
			tvConsigNum.setVisibility(View.VISIBLE);
			tvConsigNumData.setVisibility(View.VISIBLE);
			tvExporterNum.setVisibility(View.VISIBLE);
			tvExportNumData.setVisibility(View.VISIBLE);
			tvPrepName.setVisibility(View.VISIBLE);
			tvPrepNameData.setVisibility(View.VISIBLE);
			tvActiveSubs.setVisibility(View.VISIBLE);
			tvActiveSubsData.setVisibility(View.VISIBLE);
			tvMakerPrep.setVisibility(View.VISIBLE);
			tvMakerPrepData.setVisibility(View.VISIBLE);
			tvMakerAS.setVisibility(View.VISIBLE);
			tvMakerASdata.setVisibility(View.VISIBLE);
			tvImporter.setVisibility(View.VISIBLE);
			tvImporterData.setVisibility(View.VISIBLE);
			tvCertNum.setVisibility(View.VISIBLE);
			tvCertData.setVisibility(View.VISIBLE);
			tvNationRec.setVisibility(View.VISIBLE);
			tvNatRecData.setVisibility(View.VISIBLE);
			tvContainer.setVisibility(View.VISIBLE);
			tvContainerData.setVisibility(View.VISIBLE);
			tvNumUnits.setVisibility(View.VISIBLE);
			tvNumUnitsData.setVisibility(View.VISIBLE);
			Log.d("TextView", uniqID.getPreporate().getName());
			
			try {
				if (uniqID.getPrepGroupID()!=0){tvPgNumData.setText(String.valueOf(uniqID.getPrepGroupID()));}
			} catch (Exception e) {
				// TODO: handle exception
				Log.d("RECIVE-DATA", e.toString());
			}
			try {
				if (uniqID.getCosigment().getId()!=null){tvConsigNumData.setText(uniqID.getCosigment().getId().toString());}
			} catch (Exception e) {
				// TODO: handle exception
				Log.d("RECIVE-DATA", e.toString());
			}
			try {
				if (uniqID.getCosigment().getExporterNumber()!=null){tvExportNumData.setText(uniqID.getCosigment().getExporterNumber());}
			} catch (Exception e) {
				// TODO: handle exception
				Log.d("RECIVE-DATA", e.toString());
			}
			try {
				if (uniqID.getPreporate().getName()!=null){tvPrepNameData.setText(uniqID.getPreporate().getName());}
			} catch (Exception e) {
				// TODO: handle exception
				Log.d("RECIVE-DATA", e.toString());
			}
			try {
				if (uniqID.getActiveSubs().getName()!=null){tvActiveSubsData.setText(uniqID.getActiveSubs().getName());}
			} catch (Exception e) {
				// TODO: handle exception
				Log.d("RECIVE-DATA", e.toString());
			}
			try {
				if (uniqID.getPrepMaker().getName()!=null){tvMakerPrepData.setText(uniqID.getPrepMaker().getName());}
			} catch (Exception e) {
				// TODO: handle exception
				Log.d("RECIVE-DATA", e.toString());
			}
			try {
				if (uniqID.getAsMaker().getName()!=null){tvMakerASdata.setText(uniqID.getAsMaker().getName());}
			} catch (Exception e) {
				// TODO: handle exception
				Log.d("RECIVE-DATA", e.toString());
			}
			try {
				if (uniqID.getImporter().getName()!=null){tvImporterData.setText(uniqID.getImporter().getName());}
			} catch (Exception e) {
				// TODO: handle exception
				Log.d("RECIVE-DATA", e.toString());
			}
			try {
				if (uniqID.getCertImporter().getCertNum()!=null){tvCertData.setText(uniqID.getCertImporter().getCertNum());}
			} catch (Exception e) {
				// TODO: handle exception
				Log.d("RECIVE-DATA", e.toString());
			}
			try {
				if (uniqID.getCertImporter().getNationalRecNum()!=null){tvNatRecData.setText(uniqID.getCertImporter().getNationalRecNum());}
			} catch (Exception e) {
				// TODO: handle exception
				Log.d("RECIVE-DATA", e.toString());
			}
			try {
				if (uniqID.getContainer().getSize()!=0){tvContainerData.setText(uniqID.getContainer().getSize() + " " 
						+ uniqID.getContainerVolume().getName() + " " 
						+ uniqID.getContainerType().getName());}
			} catch (Exception e) {
				// TODO: handle exception
				Log.d("RECIVE-DATA", e.toString());
			}
			try {
				if (uniqID.getPgResult().getNumUnits()!=null){tvNumUnitsData.setText(uniqID.getPgResult().getNumUnits().toString());}
			} catch (Exception e) {
				// TODO: handle exception
				Log.d("RECIVE-DATA", e.toString());
			}
			
			tvResultData.setText("OK! (200)");
		}
		else {
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
	    	dialog.setTitle("ERROR");
	    	dialog.setCancelable(true);
	    	dialog.setMessage("No data!");
	    	dialog.show();
	    	tvResultData.setText("Error! (404)");
	    }		
		
	}

}

