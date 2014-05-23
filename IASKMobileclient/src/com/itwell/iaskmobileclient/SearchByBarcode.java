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
import android.support.v4.app.Fragment;
import android.annotation.TargetApi;
import android.app.AlertDialog;
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

public class SearchByBarcode extends ActionBarActivity {
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
	public static String getBarcodeURL = "/iaskdbport-portlet/api/secure/jsonws/iaskbarcode/get-ias-kbarcode-by-barcode/barcode-group/";
	public static IASKbarcodeID barcodeSearch;
	public static String userName = "username@itwell.com.ua";
	public static String userPassword = "userPassword";
	SharedPreferences sp;
	
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
		hostAddr = proto + slash + host + ":8080";
		final EditText tfBarcode = (EditText) findViewById(R.id.tfBarcode);
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
		if ((!tfBarcode.getText().toString().equals(""))&&(tfBarcode.getText().length() != 0)){
	    	    barcodeSearch = getDataSearch(tfBarcode.getText().toString());
	    	    if(barcodeSearch!=null){
	    	    	
	    	    	try {
		    	    	if (barcodeSearch.getBarcode()!=null){
		    	    		Log.d("TextView", String.valueOf(barcodeSearch.getBarcode().getBarcodeGroup()) + "start PG search");
		    	    		//
		    	    		IASKuniqID uniqID = new IASKuniqID();
		    	    		Log.d("uniqID", "uniqID.setHostAddr(hostAddr)");
		    	    		uniqID.setHostAddr(hostAddr);
		    	    		Log.d("uniqID", "uniqID.setHttpClient(barcodeSearch.getHttpClient())");
		    	    		uniqID.setHttpClient(barcodeSearch.getHttpClient());
		    	    		Log.d("uniqID", "uniqID.setPrepGroupID(Integer.valueOf(barcodeSearch.getBarcode().getPreporateGroupID().toString()))");
		    	    		uniqID.setPrepGroupID(Integer.valueOf(barcodeSearch.getBarcode().getPreporateGroupID().toString()));
		    	    		Log.d("uniqID", "getAllData");
		    	    		uniqID.getAllData();
		    	    		Log.d("uniqID", "getAllData - completed");
		    	    		IASKbarcode barcodeGroup = barcodeSearch.getBarcode();
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
			    			//Log.d("TextView", uniqID.getActiveSubs().getName());
			    			//Log.d("TextView", uniqID.getPrepMaker().getName());
			    			//Log.d("TextView", uniqID.getAsMaker().getName());
			    			//Log.d("TextView", uniqID.getImporter().getName());
			    			//Log.d("TextView", uniqID.getCertImporter().getCertNum());
			    			//Log.d("TextView", uniqID.getCertImporter().getNationalRecNum());
			    			//Log.d("TextView", uniqID.getContainer().getSize() + " " 
			    			//		+ uniqID.getContainerVolume().getName() + " " 
			    			//		+ uniqID.getContainerType().getName());
			    			//Log.d("TextView", uniqID.getPgResult().getNumUnits().toString());
			    			
			    			if (uniqID.getPrepGroupID()!=0){tvPgNumData.setText(String.valueOf(uniqID.getPrepGroupID()));}
			    			if (uniqID.getCosigment().getId()!=null){tvConsigNumData.setText(uniqID.getCosigment().getId().toString());}
			    			if (uniqID.getCosigment().getExporterNumber()!=null){tvExportNumData.setText(uniqID.getCosigment().getExporterNumber());}
			    			if (uniqID.getPreporate().getName()!=null){tvPrepNameData.setText(uniqID.getPreporate().getName());}
			    			if (uniqID.getActiveSubs().getName()!=null){tvActiveSubsData.setText(uniqID.getActiveSubs().getName());}
			    			if (uniqID.getPrepMaker().getName()!=null){tvMakerPrepData.setText(uniqID.getPrepMaker().getName());}
			    			if (uniqID.getAsMaker().getName()!=null){tvMakerASdata.setText(uniqID.getAsMaker().getName());}
			    			if (uniqID.getImporter().getName()!=null){tvImporterData.setText(uniqID.getImporter().getName());}
			    			if (uniqID.getCertImporter().getCertNum()!=null){tvCertData.setText(uniqID.getCertImporter().getCertNum());}
			    			if (uniqID.getCertImporter().getNationalRecNum()!=null){tvNatRecData.setText(uniqID.getCertImporter().getNationalRecNum());}
			    			if (uniqID.getContainer().getSize()!=0){tvContainerData.setText(uniqID.getContainer().getSize() + " " 
			    					+ uniqID.getContainerVolume().getName() + " " 
			    					+ uniqID.getContainerType().getName());}
			    			if (uniqID.getPgResult().getNumUnits()!=null){tvNumUnitsData.setText(uniqID.getPgResult().getNumUnits().toString());}
			    			tvResultData.setText("OK! (200)");
		    	    	}
		    	    	else {
		    	    		Log.d("TextView", "barcodeSearchGetBarcode NULLLL");
		    	    	}
	    	    	}
	    	    	catch (Exception e){
	    	    		Log.d("TextViewException", "barcodeSearchGetBarcode NULLLL");
	    	    	}
	    	    	/*IASKuniqID uniqID = barcodeSearch.getUniqID();
	    	    	
	    			Log.d("TextView", "CHECKING...");
	    			*/
	    			//Log.d("TextView", String.valueOf(uniqID.getPrepGroupID()));
	    			//Log.d("TextView", uniqID.getCosigment().getId().toString());
	    			/*
	    			 * uniqID имеет null на данном этапе!!!!
	    			 */
	    			/*try {
	    				Log.d("TextView", uniqID.getCosigment().getExporterNumber());
	    			}
	    			catch (Exception e){
	    				Log.d("TextView", "We have a problem!");
	    				if (uniqID!=null){
	    					Log.d("CHECK", "not null");
	    				}
	    				else {
	    					Log.d("CHECK", "null");
	    				}
	    			
	    				//Log.d("TextView", e.getLocalizedMessage());
	    			}
	    			Log.d("TextView", uniqID.getPreporate().getName());
	    			Log.d("TextView", uniqID.getActiveSubs().getName());
	    			Log.d("TextView", uniqID.getPrepMaker().getName());
	    			Log.d("TextView", uniqID.getAsMaker().getName());
	    			Log.d("TextView", uniqID.getImporter().getName());
	    			Log.d("TextView", uniqID.getCertImporter().getCertNum());
	    			Log.d("TextView", uniqID.getCertImporter().getNationalRecNum());
	    			Log.d("TextView", uniqID.getContainer().getSize() + " " 
	    					+ uniqID.getContainerVolume().getName() + " " 
	    					+ uniqID.getContainerType().getName());
	    			Log.d("TextView", uniqID.getPgResult().getNumUnits().toString());
	    			
	    			
	    			//tvPgNumData.setText(String.valueOf(uniqID.getPrepGroupID()));
	    			//tvConsigNumData.setText(uniqID.getCosigment().getId().toString());
	    			tvExportNumData.setText(uniqID.getCosigment().getExporterNumber());
	    			tvPrepNameData.setText(uniqID.getPreporate().getName());
	    			tvActiveSubsData.setText(uniqID.getActiveSubs().getName());
	    			tvMakerPrepData.setText(uniqID.getPrepMaker().getName());
	    			tvMakerASdata.setText(uniqID.getAsMaker().getName());
	    			tvImporterData.setText(uniqID.getImporter().getName());
	    			tvCertData.setText(uniqID.getCertImporter().getCertNum());
	    			tvNatRecData.setText(uniqID.getCertImporter().getNationalRecNum());
	    			tvContainerData.setText(uniqID.getContainer().getSize() + " " 
	    					+ uniqID.getContainerVolume().getName() + " " 
	    					+ uniqID.getContainerType().getName());
	    			tvNumUnitsData.setText(uniqID.getPgResult().getNumUnits().toString());*/
	    			
	    	    }
	    	    
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
			/*IASKuniqID uniqID = new IASKuniqID();
			uniqID.setHostAddr(hostAddr);
			uniqID.setHttpClient(httpClient);
			Log.d("BARCODE", barcodeGroup.getPreporateGroupID().toString());
			uniqID.setPrepGroupID(Integer.valueOf(barcodeGroup.getPreporateGroupID().toString()));
			Log.d("BARCODE", "start getalldata");
			uniqID.getAllData();
			Log.d("BARCODE", "getalldata - OK!");
			barcodeSearch.setUniqID(uniqID);
			Log.d("BARCODE", "set barcodeSearch");
			if (barcodeSearch.getUniqID()!=null){
				Log.d("BARCODE", "get barcodeSearch not null");
			}
			else {
				Log.d("BARCODE", "get barcodeSearch is null");
			}
			
			result=barcodeSearch;*/
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
	public static IASKbarcode getBGdata(DefaultHttpClient httpClient, String barcode) {
		IASKbarcode result = null;
		//IASKbarcode barcodeGroup =null;
		String secureURLpg = hostAddr + getBarcodeURL + barcode;
		String nonSecureURL = secureURLpg;
		//tvResultData.setText(secureURLpg);
		Log.d("GetBGData", nonSecureURL);
		try {
			URI uri;
	        InputStream data = null;
	        HttpGet httpGet = new HttpGet(nonSecureURL);
	        HttpResponse response;
	        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
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
		        	result =readFieldBG(reader);
		        	barcodeSearch.setBarcode(result);
		        	/*IASKuniqID uniqID = new IASKuniqID();
		        	uniqID.setHostAddr(hostAddr);
		        	uniqID.setHttpClient(httpClient);
		        	barcodeSearch.setUniqID(uniqID);*/
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
}
