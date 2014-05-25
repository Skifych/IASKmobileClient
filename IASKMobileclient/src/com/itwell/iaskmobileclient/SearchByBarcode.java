package com.itwell.iaskmobileclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.stream.JsonReader;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.internal.widget.ActivityChooserModel.HistoricalRecord;
import android.support.v4.app.Fragment;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
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
import android.app.AlertDialog;
import android.app.Dialog.*;
import android.content.Intent;
import android.content.SharedPreferences;

import org.apache.http.util.EntityUtils;

import com.google.gson.stream.JsonReader;
import com.itwell.iaskmobileclient.SearchByUniqID.DownloadDataTask;

public class SearchByBarcode extends ActionBarActivity {
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
	public static String getBarcodeURL = "/iaskdbport-portlet/api/secure/jsonws/iaskbarcode/get-ias-kbarcode-by-barcode/barcode-group/";
	public static IASKbarcodeID barcodeSearch;
	public static String userName = "username@itwell.com.ua";
	public static String userPassword = "userPassword";
	SharedPreferences sp;
	
	
	EditText tfBarcode;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_by_barcode);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		barcodeSearch = new IASKbarcodeID();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_by_barcode, menu);
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
					R.layout.fragment_search_by_barcode, container, false);
			return rootView;
		}
	}
	
	public void showSettings()
	{
		Intent i = new Intent(this, IASKmobileSettings.class);
		startActivity(i);
	}
	
	public void onClickSearch(View v) {
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		proto = sp.getString("protoList", "http");
		host = sp.getString("hostAddr", "127.0.0.1");
		userName = sp.getString("userName", userName);
		userPassword = sp.getString("userPassword", userPassword);
		port = sp.getString("port", "8080");
		//hostAddr = proto + slash + host + ":8080";
		hostAddr = getHostAddr(proto, host, port);
		tfBarcode = (EditText) findViewById(R.id.tfBarcode);
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
		if ((!tfBarcode.getText().toString().equals(""))&&(tfBarcode.getText().length() != 0)){
			DownloadDataTask ddt = new DownloadDataTask();
			ddt.execute();
	    	    
		}
		else {
			tvResultData.setText("Insert data");
		}
    }
	
	public IASKbarcodeID getDataSearch(String barcode){
		IASKbarcodeID result = new IASKbarcodeID();
		Log.d("BARCODE", hostAddr);
		barcodeSearch.setHostAddr(hostAddr);
		final EditText tfBarcode = (EditText) findViewById(R.id.tfBarcode);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		//tvResultData.setText("httpClient");
		httpClient.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
				new UsernamePasswordCredentials(userName, userPassword));
		//AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		IASKbarcode barcodeGroup = null;
		
		String secureURLpg = hostAddr + getBarcodeURL + barcode;
		String nonSecureURL = secureURLpg;
		barcodeGroup = getBGdata(httpClient, barcode);
		if ((barcodeGroup!=null)&&(barcodeGroup.getPreporateGroupID()!=null)){
			
			//result = new IASKbarcodeID();
			Log.d("barcodeSearch", "result.setBarcode()");
			result.setBarcode(barcodeGroup);
			result.setHostAddr(hostAddr);
			result.setHttpClient(httpClient);
			Log.d("barcodeSearch", barcodeGroup.getBarcodeGroup());
			try {
    	    	if (barcodeSearch.getBarcode()!=null){
    	    		Log.d("barcodeSearch", "Result: ");
    	    		Log.d("barcodeSearch", barcodeGroup.getBarcodeGroup());
    	    		Log.d("barcodeSearch", result.getBarcode().getBarcodeGroup());
    	    	}
    	    	else {
    	    		Log.d("barcodeSearch", "GetBarcode NULLLL");
    	    	}
	    	}
	    	catch (Exception e){
	    		Log.d("barcodeSearch", "GetBarcode ---- NULLLL!!!!!!!!");
	    	}
		}
		else {
			result =null;
		}
		return result;
	}
	
	public static IASKbarcode readFieldBG(JsonReader reader) throws IOException {
		Long id =null;;
		String barcodeGroup =null;;
		Long preporateGroupID =null;;
		Long consigmentID =null;;
		
	     reader.beginObject();
	     while (reader.hasNext()) {
	       String jname = reader.nextName();
	       if (jname.equals("id")) {
	         id = reader.nextLong();
	       } else if (jname.equals("barcodeGroup")) {
	    	   barcodeGroup = reader.nextString();
	       } else if (jname.equals("preporateGroupID")) {
	    	   preporateGroupID = reader.nextLong();
		   } else if (jname.equals("consigmentID")) {
			   consigmentID = reader.nextLong();
		   }
	       else {
	         reader.skipValue();
	       }
	     }
	     reader.endObject();
	     IASKbarcode result = new IASKbarcode();
	     result.setId(id);
	     result.setBarcodeGroup(barcodeGroup);
	     result.setPreporateGroupID(preporateGroupID);
	     result.setConsigmentID(consigmentID);
	     return result;
		}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static IASKbarcode getBGdata(DefaultHttpClient httpClient, String url) {
		IASKbarcode result = null;
		//IASKbarcode barcodeGroup =null;
		//String secureURLpg = hostAddr + getBarcodeURL + barcode;
		Log.d("getBGdata", "set nonSecureURL url");
		String nonSecureURL = url;
		//tvResultData.setText(secureURLpg);
		Log.d("GetBGData", nonSecureURL);
		try {
			Log.d("GetBGData", "httpGet");
			URI uri;
	        InputStream data = null;
	        HttpGet httpGet = new HttpGet(nonSecureURL);
	        Log.d("GetBGData", "response");
	        HttpResponse response;
	        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        //StrictMode.setThreadPolicy(policy);
	        Log.d("GetBGData", "HTTP response");
			response = httpClient.execute(httpGet);
			//System.out.println(response.getStatusLine().getStatusCode());
			String status = response.getStatusLine().toString();
			Log.d("GetBGData", response.getStatusLine().toString());
	        if (response.getStatusLine().getStatusCode() == 200) {
	        	HttpEntity entity = response.getEntity();
	        	String jsonContent = EntityUtils.toString(entity);
	        	
	        	String except = "\"exception\":";
	   		    //String except2 = "\"exception\":\"Invalid authentication token";
	   		    Pattern pattern = Pattern.compile(except);
	   		    //Pattern pattern2 = Pattern.compile(except2);
	   		    Matcher matcher = pattern.matcher(jsonContent);
	   		    //Matcher matcher2 = pattern2.matcher(jsonContent);
				if (matcher.find()){
					Log.d("GetBGData", "ERROR! " + jsonContent);
					return null;
				}
				else {
					Log.d("GetBGData", "OK! " + jsonContent);
		        	Reader stringReader = new StringReader(jsonContent);
		        	JsonReader reader = new JsonReader(stringReader);
		        	Log.d("GetBGData", "result");
		        	result = readFieldBG(reader);
		        	Log.d("GetBGData", result.getPreporateGroupID().toString());
		        	//barcodeSearch.setBarcode(result);
				}
	        	     
	        }
	        else {
	        	//System.out.println(response.getStatusLine());
	        	Log.d("HTTPCLIENT", response.getStatusLine().toString());
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("HTTPCLIENT", e.getLocalizedMessage());
		}
		if (result!=null) {
			Log.d("GetBGData", "RESULT not NULL");
		}
		else {
			Log.d("GetBGData", "RESULT is NULL");
		}
		return result;
	}

	public class IASKbarcodeID {
		private IASKbarcode barcode;
		//private IASKuniqID uniqID;
		private String hostAddr;
		private DefaultHttpClient httpClient;
		//private int preporateGroupID;
		
		public IASKbarcode getBarcode() {
			return barcode;
		}
		public void setBarcode(IASKbarcode barcode) {
			Log.d("setBarcode", barcode.toString() + " ---> 422");
			Log.d("setBarcode", barcode.getBarcodeGroup() + " ---> 423");
			this.barcode = barcode;
		}
		/*public IASKuniqID getUniqID() {
			return uniqID;
		}
		public void setUniqID(IASKuniqID uniqbarcodeID) {
			this.uniqID = uniqID;
		}*/
		
		public String getHostAddr() {
			return this.hostAddr;
		}
		public void setHostAddr(String hostAddr){
			this.hostAddr = hostAddr;
		}
		
		public DefaultHttpClient getHttpClient() {
			return httpClient;
		}
		public void setHttpClient(DefaultHttpClient httpClient) {
			this.httpClient = httpClient;
		}
		/*
		public int getPreporateGroupID() {
			return preporateGroupID;
		}
		public void setPreporateGroupID(int preporateGroupID) {
			this.preporateGroupID = preporateGroupID;
		}*/
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
		IASKbarcodeID result = new IASKbarcodeID();
		String barcode = tfBarcode.getText().toString();
		IASKbarcode barcodeGroup = null;
		String url = null;
		//String hostAddrDDT = null;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			tvResultData.setText("Getting data...");
			Log.d("BARCODE", hostAddr);
			barcodeSearch.setHostAddr(hostAddr);
			Log.d("uniqID", "uniqID.setHostAddr(hostAddr)");
			Log.d("uniqID", hostAddr);
			ddtUniqID.setHostAddr(hostAddr);
			EditText tfBarcode = (EditText) findViewById(R.id.tfBarcode);
			String secureURLpg = hostAddr + getBarcodeURL + barcode;
			String nonSecureURL = secureURLpg;
			//hostAddrDDT = hostAddr;
			
			url = hostAddr + getBarcodeURL + barcode;
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
			DefaultHttpClient httpClient = new DefaultHttpClient();
			//tvResultData.setText("httpClient");
			httpClient.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
					new UsernamePasswordCredentials(userName, userPassword));
			Log.d("barcodeGroup", "getBGdata(httpClient, url)");
			Log.d("barcodeGroup", url);
			barcodeGroup = getBGdata(httpClient, url);
			if (barcodeGroup!=null){
				Log.d("DownloadDataTask", "barcodeGroup not NULL");
				if (barcodeGroup.getPreporateGroupID()!=null){
					Log.d("DownloadDataTask", barcodeGroup.getPreporateGroupID().toString());
					Log.d("uniqID", "uniqID.setHttpClient(barcodeSearch.getHttpClient())");
    	    		ddtUniqID.setHttpClient(httpClient);
    	    		Log.d("uniqID", "uniqID.setPrepGroupID(Integer.valueOf(barcodeGroup.getPreporateGroupID().toString()))");
    	    		ddtUniqID.setPrepGroupID(Integer.valueOf(barcodeGroup.getPreporateGroupID().toString()));
    	    		Log.d("uniqID", barcodeGroup.getPreporateGroupID().toString());
    	    		
    	    		Log.d("uniqID", "getAllData");
    	    		ddtUniqID.getAllData();
    	    		Log.d("uniqID", "getAllData - completed");
				}
				else {
    	    		Log.d("barcodeSearch", "GetBarcode NULLLL");
    	    	}
			}
			else {
	    		Log.d("barcodeSearch", "GetBarcode NULLLL2");
	    	}	
			return null;
		}
		
	}
}
