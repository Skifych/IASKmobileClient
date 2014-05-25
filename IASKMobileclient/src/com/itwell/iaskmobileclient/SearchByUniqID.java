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
import android.os.AsyncTask;
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
	public static String port = "8080";
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
	
	
	EditText tfUniqID;
	TextView tvResultData;
	TextView tvPgNum;
	TextView tvPgNumData;
	TextView tvConsigNum;
	TextView tvConsigNumData;
	TextView tvExporterNum;
	TextView tvExportNumData;
	TextView tvPrepName;
	TextView tvPrepNameData;
	TextView tvActiveSubs;
	TextView tvActiveSubsData;
	TextView tvMakerPrep;
	TextView tvMakerPrepData;
	TextView tvMakerAS;
	TextView tvMakerASdata;
	TextView tvImporter;
	TextView tvImporterData;
	TextView tvCertNum;
	TextView tvCertData;
	TextView tvNationRec;
	TextView tvNatRecData;
	TextView tvContainer;
	TextView tvContainerData;
	TextView tvNumUnits;
	TextView tvNumUnitsData;
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
		tfUniqID = (EditText) findViewById(R.id.tfUniqID);
		tvResultData = (TextView) findViewById(R.id.tvResultData);
		tvPgNum = (TextView) findViewById(R.id.tvPgNum);
		tvPgNumData = (TextView) findViewById(R.id.tvPgNumData);
		tvConsigNum = (TextView) findViewById(R.id.tvConsigNum);
		tvConsigNumData = (TextView) findViewById(R.id.tvConsigNumData);
		tvExporterNum = (TextView) findViewById(R.id.tvExporterNum);
		tvExportNumData = (TextView) findViewById(R.id.tvExportNumData);
		tvPrepName = (TextView) findViewById(R.id.tvPrepName);
		tvPrepNameData = (TextView) findViewById(R.id.tvPrepNameData);
		tvActiveSubs = (TextView) findViewById(R.id.tvActiveSubs);
		tvActiveSubsData = (TextView) findViewById(R.id.tvActiveSubsData);
		tvMakerPrep = (TextView) findViewById(R.id.tvMakerPrep);
		tvMakerPrepData = (TextView) findViewById(R.id.tvMakerPrepData);
		tvMakerAS = (TextView) findViewById(R.id.tvMakerAS);
		tvMakerASdata = (TextView) findViewById(R.id.tvMakerASdata);
		tvImporter = (TextView) findViewById(R.id.tvImporter);
		tvImporterData = (TextView) findViewById(R.id.tvImporterData);
		tvCertNum = (TextView) findViewById(R.id.tvCertNum);
		tvCertData = (TextView) findViewById(R.id.tvCertData);
		tvNationRec = (TextView) findViewById(R.id.tvNationRec);
		tvNatRecData = (TextView) findViewById(R.id.tvNationRecData);
		tvContainer = (TextView) findViewById(R.id.tvContainer);
		tvContainerData = (TextView) findViewById(R.id.tvContainerData);
		tvNumUnits = (TextView) findViewById(R.id.tvNumUnits);
		tvNumUnitsData = (TextView) findViewById(R.id.tvNumUnitsData);
		
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
		//hostAddr = proto + slash + host + ":8080";
		hostAddr = getHostAddr(proto, host, port);
		IASKuniqID uniqID = new IASKuniqID();
		tvResultData = (TextView) findViewById(R.id.tvResultData);
		tvPgNum = (TextView) findViewById(R.id.tvPgNum);
		tvPgNumData = (TextView) findViewById(R.id.tvPgNumData);
		tvConsigNum = (TextView) findViewById(R.id.tvConsigNum);
		tvConsigNumData = (TextView) findViewById(R.id.tvConsigNumData);
		tvExporterNum = (TextView) findViewById(R.id.tvExporterNum);
		tvExportNumData = (TextView) findViewById(R.id.tvExportNumData);
		tvPrepName = (TextView) findViewById(R.id.tvPrepName);
		tvPrepNameData = (TextView) findViewById(R.id.tvPrepNameData);
		tvActiveSubs = (TextView) findViewById(R.id.tvActiveSubs);
		tvActiveSubsData = (TextView) findViewById(R.id.tvActiveSubsData);
		tvMakerPrep = (TextView) findViewById(R.id.tvMakerPrep);
		tvMakerPrepData = (TextView) findViewById(R.id.tvMakerPrepData);
		tvMakerAS = (TextView) findViewById(R.id.tvMakerAS);
		tvMakerASdata = (TextView) findViewById(R.id.tvMakerASdata);
		tvImporter = (TextView) findViewById(R.id.tvImporter);
		tvImporterData = (TextView) findViewById(R.id.tvImporterData);
		tvCertNum = (TextView) findViewById(R.id.tvCertNum);
		tvCertData = (TextView) findViewById(R.id.tvCertData);
		tvNationRec = (TextView) findViewById(R.id.tvNationRec);
		tvNatRecData = (TextView) findViewById(R.id.tvNationRecData);
		tvContainer = (TextView) findViewById(R.id.tvContainer);
		tvContainerData = (TextView) findViewById(R.id.tvContainerData);
		tvNumUnits = (TextView) findViewById(R.id.tvNumUnits);
		tvNumUnitsData = (TextView) findViewById(R.id.tvNumUnitsData);
		
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
		DownloadDataTask ddt = new DownloadDataTask();
		ddt.execute();
		
		
	}
	
	private String getHostAddr(String proto, String host, String port){
		String result;
		//Pattern pattern = Pattern.compile(port);
		if ((port.equals("80")|(port.equals("0"))|(port.equals("")))){
			result = proto + slash + host;
		}
		else {
			result = proto + slash + host + ":" + port;
		}
		return result;
	}
	 
	protected class DownloadDataTask  extends AsyncTask<Void, Void, Void>  {
		IASKuniqID ddtUniqID = new IASKuniqID();
		Integer urlID = 0;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			tvResultData.setText("Getting data...");
			Log.d("uniqID", "uniqID.setHostAddr(hostAddr)");
			Log.d("uniqID", hostAddr);
			ddtUniqID.setHostAddr(hostAddr);
			
			Log.d("uniqID", "uniqID.setPrepGroupID(Integer.valueOf(barcodeSearch.getBarcode().getPreporateGroupID().toString()))");
			urlID = Integer.valueOf(tfUniqID.getText().toString());
			ddtUniqID.setPrepGroupID(urlID);
			
			
		}
		
		protected void onProgressUpdate(Integer... progress) {
	         setProgress(progress[0]);
	     }

	     protected void onPostExecute(Void result) {
	    	 super.onPostExecute(result);
	         //showDialog("Downloaded " + result + " bytes");
	    	 if ((ddtUniqID.resultOK()==true)&&(ddtUniqID.getPgResult()!=null)){
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
					Log.d("TextView", ddtUniqID.getPreporate().getName());
					
					try {
						if (ddtUniqID.getPrepGroupID()!=0){tvPgNumData.setText(String.valueOf(ddtUniqID.getPrepGroupID()));}
					} catch (Exception e) {
						// TODO: handle exception
						Log.d("RECIVE-DATA", e.toString());
					}
					try {
						if (ddtUniqID.getCosigment().getId()!=null){tvConsigNumData.setText(ddtUniqID.getCosigment().getId().toString());}
					} catch (Exception e) {
						// TODO: handle exception
						Log.d("RECIVE-DATA", e.toString());
					}
					try {
						if (ddtUniqID.getCosigment().getExporterNumber()!=null){tvExportNumData.setText(ddtUniqID.getCosigment().getExporterNumber());}
					} catch (Exception e) {
						// TODO: handle exception
						Log.d("RECIVE-DATA", e.toString());
					}
					try {
						if (ddtUniqID.getPreporate().getName()!=null){tvPrepNameData.setText(ddtUniqID.getPreporate().getName());}
					} catch (Exception e) {
						// TODO: handle exception
						Log.d("RECIVE-DATA", e.toString());
					}
					try {
						if (ddtUniqID.getActiveSubs().getName()!=null){tvActiveSubsData.setText(ddtUniqID.getActiveSubs().getName());}
					} catch (Exception e) {
						// TODO: handle exception
						Log.d("RECIVE-DATA", e.toString());
					}
					try {
						if (ddtUniqID.getPrepMaker().getName()!=null){tvMakerPrepData.setText(ddtUniqID.getPrepMaker().getName());}
					} catch (Exception e) {
						// TODO: handle exception
						Log.d("RECIVE-DATA", e.toString());
					}
					try {
						if (ddtUniqID.getAsMaker().getName()!=null){tvMakerASdata.setText(ddtUniqID.getAsMaker().getName());}
					} catch (Exception e) {
						// TODO: handle exception
						Log.d("RECIVE-DATA", e.toString());
					}
					try {
						if (ddtUniqID.getImporter().getName()!=null){tvImporterData.setText(ddtUniqID.getImporter().getName());}
					} catch (Exception e) {
						// TODO: handle exception
						Log.d("RECIVE-DATA", e.toString());
					}
					try {
						if (ddtUniqID.getCertImporter().getCertNum()!=null){tvCertData.setText(ddtUniqID.getCertImporter().getCertNum());}
					} catch (Exception e) {
						// TODO: handle exception
						Log.d("RECIVE-DATA", e.toString());
					}
					try {
						if (ddtUniqID.getCertImporter().getNationalRecNum()!=null){tvNatRecData.setText(ddtUniqID.getCertImporter().getNationalRecNum());}
					} catch (Exception e) {
						// TODO: handle exception
						Log.d("RECIVE-DATA", e.toString());
					}
					try {
						if (ddtUniqID.getContainer().getSize()!=0){tvContainerData.setText(ddtUniqID.getContainer().getSize() + " " 
								+ ddtUniqID.getContainerVolume().getName() + " " 
								+ ddtUniqID.getContainerType().getName());}
					} catch (Exception e) {
						// TODO: handle exception
						Log.d("RECIVE-DATA", e.toString());
					}
					try {
						if (ddtUniqID.getPgResult().getNumUnits()!=null){tvNumUnitsData.setText(ddtUniqID.getPgResult().getNumUnits().toString());}
					} catch (Exception e) {
						// TODO: handle exception
						Log.d("RECIVE-DATA", e.toString());
					}
					
					tvResultData.setText("OK! (200)");
				}
	    	 else {
	 			tvResultData.setText("Error! (404)");
	 	    }
	    	 

	     }

		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			DefaultHttpClient httpClient = new DefaultHttpClient();
			httpClient.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
					new UsernamePasswordCredentials(userName, userPassword));
			
			Log.d("uniqID", "uniqID.setHttpClient(barcodeSearch.getHttpClient())");
			ddtUniqID.setHttpClient(httpClient);
			
			Log.d("uniqID", "getAllData");
			ddtUniqID.getAllData();
			Log.d("uniqID", "getAllData - completed");
						
			return null;
		}
		
	}

}

