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

		
		
		//String nonSecureURL = "http://192.168.27.7:8080/TryToTestDBchange-portlet/api/jsonws/trytoaddsomefilelds/get-all-tryto-addsomefilelds";
		//String SecureURL = "http://192.168.27.7:8080/TryToTestDBchange-portlet/api/secure/jsonws/trytoaddsomefilelds/get-all-tryto-addsomefilelds";
		IASKpreporateGroup pgResult = null;
		IASKpreporate preporate = null;
		IASKactiveSubstance activeSubs = null;
		IASKcontainer container = null;
		IASKcontainerType contType = null;
		IASKcontainerVolume contVolume = null;
		IASKcontragent prepMaker = null;
		IASKcontragent asMaker = null;
		IASKcontragent importer = null;
		IASKimporterCertificate certImport = null;
		IASKconsigment consigment = null;
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		//tvResultData.setText("httpClient");
		httpClient.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
				new UsernamePasswordCredentials(userName, userPassword));
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		pgResult = getPGdata(tvResultData, httpClient, urlID);
		
	    dialog.setTitle("Preporate name ID");
	    //Add a message or buttons
	    if (pgResult!=null){

			tvPgNum.setVisibility(View.VISIBLE);
			tvPgNumData.setVisibility(View.VISIBLE);
			tvPgNumData.setText(pgResult.getId().toString());
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
	    	
			consigment = getConsigData(tvResultData, httpClient, pgResult.getConsigmentID());
	    	preporate = getPrepData(httpClient, pgResult.getPreporateNameID());
	    	activeSubs = getASdata(tvResultData, httpClient,preporate.getActiveSubstanceID());
	    	container = getContainerData(tvResultData, httpClient, pgResult.getContainerID());
	    	contType = getCTData(tvResultData, httpClient, container.getMaterialID());
	    	contVolume = getCVData(tvResultData, httpClient, container.getVolumeID());
	    	prepMaker = getCAData(tvResultData, httpClient, pgResult.getMakerPrepID());
	    	asMaker = getCAData(tvResultData, httpClient, pgResult.getMakerActiveSubsID());
	    	importer = getCAData(tvResultData, httpClient, pgResult.getImporterID());
	    	certImport = getICData(tvResultData, httpClient, pgResult.getImporterCertificateID());
	    	
	    	tvPgNumData.setText(pgResult.getId().toString());
	    	tvConsigNumData.setText(pgResult.getConsigmentID().toString());
	    	tvExportNumData.setText(consigment.getExporterNumber());
	    	tvPrepNameData.setText(preporate.getName());
	    	tvActiveSubsData.setText(activeSubs.getName());
	    	tvMakerPrepData.setText(prepMaker.getName());
	    	tvMakerASdata.setText(asMaker.getName());
	    	tvImporterData.setText(importer.getName());
	    	tvCertData.setText(certImport.getCertNum());
	    	tvNatRecData.setText(certImport.getNationalRecNum());
	    	tvContainerData.setText(container.getSize() + " " +contVolume.getName() + " " + contType.getName());
	    	tvNumUnitsData.setText(pgResult.getNumUnits().toString());
	    	tvResultData.setText("OK! (200)");
	    	//
	    	/*dialog.setCancelable(true);
	    	dialog.setMessage(preporate.getActiveSubstanceID() + " : " 
	    	+ preporate.getName() + " : " + activeSubs.getName() + " : "
	    	+ pgResult.getContainerID() + " : " 
	    	+ container.getMaterialID()  + " : "  + contType.getName() + 
	    	" : "  + contVolume.getName() + 
	    	" : prepMaker "  + prepMaker.getName() + 
	    	" : asMaker "  + asMaker.getName() + 
	    	" : importer "  + importer.getName() + 
	    	" : importer cert "  + certImport.getCertNum() +
	    	" : cert date "  + certImport.getCertRegDate().toString() +
	    	"---FIN"
	    	);
	    	dialog.show();*/
	    	
	    }
	    else {
	    	dialog.setTitle("ERROR");
	    	dialog.setCancelable(true);
	    	dialog.setMessage("No data!");
	    	dialog.show();
	    }
	    
		
	}
	
	public static IASKpreporateGroup readFieldPG(JsonReader reader) throws IOException {
		Long id = null;
		Long preporateNameID = null;
		Long containerID = null;
		Long numUnits = null;
		Long size = null;
		Long assignmentID = null;
		Long importerID = null;
		Long importerCertificateID = null;
		Long makerPrepID = null;
		Long makerActiveSubsID = null;
		Long consigmentID = null;

	     reader.beginObject();
	     while (reader.hasNext()) {
	       String name = reader.nextName();
	       if (name.equals("id")) {
	         id = reader.nextLong();
	       } else if (name.equals("preporateNameID")) {
	    	   preporateNameID = reader.nextLong();
	       } else if (name.equals("containerID")) {
	    	   containerID = reader.nextLong();
		   } else if (name.equals("numUnits")) {
			   numUnits = reader.nextLong();
		   } else if (name.equals("size")) {
			   size = reader.nextLong();
		   } else if (name.equals("assignmentID")) {
			   assignmentID = reader.nextLong();
		   } else if (name.equals("importerCertificateID")) {
			   importerCertificateID = reader.nextLong();
		   } else if (name.equals("makerPrepID")) {
			   makerPrepID = reader.nextLong();
		   } else if (name.equals("makerActiveSubsID")) {
			   makerActiveSubsID = reader.nextLong();
		   } else if (name.equals("consigmentID")) {
			   consigmentID = reader.nextLong();
		   } else if (name.equals("importerID")) {
			   importerID = reader.nextLong();
		   }
	       else {
	         reader.skipValue();
	       }
	     }
	     reader.endObject();
	     IASKpreporateGroup result = new IASKpreporateGroup();
	     //Date date2 = new Date(date);
	     result.setId(id);
	     result.setPreporateNameID(preporateNameID);
	     result.setContainerID(containerID);
	     result.setNumUnits(numUnits);
	     result.setSize(size);
	     result.setAssignmentID(assignmentID);
	     result.setImporterCertificateID(importerCertificateID);
	     result.setMakerPrepID(makerPrepID);
	     result.setMakerActiveSubsID(makerActiveSubsID);
	     result.setConsigmentID(consigmentID);
	     result.setImporterID(importerID);
	     
	     
	     //System.out.println("id: " + id + " city: " + city + " date: "+ date + "(date2) " + date2 +  " location: "+ location);
	     return result;
		}

	public static IASKpreporate readFieldPrep(JsonReader reader) throws IOException {
		Long id = null;
		String name = null;
		Long activeSubstanceID = null;
		Long importerID = null;

	     reader.beginObject();
	     while (reader.hasNext()) {
	       String jname = reader.nextName();
	       if (jname.equals("id")) {
	         id = reader.nextLong();
	       } else if (jname.equals("name")) {
	    	   name = reader.nextString();
	       } else if (jname.equals("activeSubstanceID")) {
	    	   activeSubstanceID = reader.nextLong();
		   } else if (jname.equals("importerID")) {
			   importerID = reader.nextLong();
		   } 
	       else {
	         reader.skipValue();
	       }
	     }
	     reader.endObject();
	     IASKpreporate result = new IASKpreporate();
	     //Date date2 = new Date(date);
	     result.setId(id);
	     result.setName(name);
	     result.setActiveSubstanceID(activeSubstanceID);
	     result.setImporterID(importerID);
	     
	     
	     //System.out.println("id: " + id + " city: " + city + " date: "+ date + "(date2) " + date2 +  " location: "+ location);
	     return result;
		}

	public static IASKactiveSubstance readFieldAS(JsonReader reader) throws IOException {
		Long id = null;
		String name = null;

	     reader.beginObject();
	     while (reader.hasNext()) {
	       String jname = reader.nextName();
	       if (jname.equals("id")) {
	         id = reader.nextLong();
	       } else if (jname.equals("name")) {
	    	   name = reader.nextString();
	       } 
	       else {
	         reader.skipValue();
	       }
	     }
	     reader.endObject();
	     IASKactiveSubstance result = new IASKactiveSubstance();
	     //Date date2 = new Date(date);
	     result.setId(id);
	     result.setName(name);
	     
	     
	     //System.out.println("id: " + id + " city: " + city + " date: "+ date + "(date2) " + date2 +  " location: "+ location);
	     return result;
		}

	public static IASKcontainer readFieldContainer(JsonReader reader) throws IOException {
		Long id = null;
		Long materialID = null;
		Long volumeID = null;
		Double size = 0d;	

	     reader.beginObject();
	     while (reader.hasNext()) {
	       String jname = reader.nextName();
	       if (jname.equals("id")) {
	         id = reader.nextLong();
	       } else if (jname.equals("materialID")) {
	    	   materialID = reader.nextLong();
	       } else if (jname.equals("volumeID")) {
	    	   volumeID = reader.nextLong();
	       } else if (jname.equals("size")) {
	    	   size = reader.nextDouble();
	       } 
	       else {
	         reader.skipValue();
	       }
	     }
	     reader.endObject();
	     IASKcontainer result = new IASKcontainer();
	     //Date date2 = new Date(date);
	     result.setId(id);
	     result.setMaterialID(materialID);
	     result.setVolumeID(volumeID);
	     result.setSize(size);
	     if (result!=null) {
	    	 Log.d("HTTPCLIENT", "container not null");
	    	 Log.d("HTTPCLIENT", result.getMaterialID().toString());
	     }
	     else {
	    	 Log.d("HTTPCLIENT", "container null");	    	 
	     }
	     return result;
		}

	public static IASKcontainerType readFieldCT(JsonReader reader) throws IOException {
		Long id = null;
		String name = null;

	     reader.beginObject();
	     while (reader.hasNext()) {
	       String jname = reader.nextName();
	       if (jname.equals("id")) {
	         id = reader.nextLong();
	       } else if (jname.equals("name")) {
	    	   name = reader.nextString();
	       }  
	       else {
	         reader.skipValue();
	       }
	     }
	     reader.endObject();
	     IASKcontainerType result = new IASKcontainerType();
	     //Date date2 = new Date(date);
	     result.setId(id);
	     result.setName(name);
	     
	     
	     //System.out.println("id: " + id + " city: " + city + " date: "+ date + "(date2) " + date2 +  " location: "+ location);
	     return result;
		}

	public static IASKcontainerVolume readFieldCV(JsonReader reader) throws IOException {
		Long id = null;
		String name = null;

	     reader.beginObject();
	     while (reader.hasNext()) {
	       String jname = reader.nextName();
	       if (jname.equals("id")) {
	         id = reader.nextLong();
	       } else if (jname.equals("name")) {
	    	   name = reader.nextString();
	       }  
	       else {
	         reader.skipValue();
	       }
	     }
	     reader.endObject();
	     IASKcontainerVolume result = new IASKcontainerVolume();
	     //Date date2 = new Date(date);
	     result.setId(id);
	     result.setName(name);
	     
	     
	     //System.out.println("id: " + id + " city: " + city + " date: "+ date + "(date2) " + date2 +  " location: "+ location);
	     return result;
		}

	public static IASKcontragent readFieldCA(JsonReader reader) throws IOException {
		Long id = null;
		String name = null;
		Long countryID = null;
		String address = null;
		String edrpou = null;
		String nds_certificate = null;

	     reader.beginObject();
	     while (reader.hasNext()) {
	       String jname = reader.nextName();
	       if (jname.equals("id")) {
	         id = reader.nextLong();
	       } else if (jname.equals("name")) {
	    	   name = reader.nextString();
	       }  else if (jname.equals("countryID")) {
	    	   countryID = reader.nextLong();
	       }  else if (jname.equals("address")) {
	    	   address = reader.nextString();
	       }  else if (jname.equals("edrpou")) {
	    	   edrpou = reader.nextString();
	       }  else if (jname.equals("nds_certificate")) {
	    	   nds_certificate = reader.nextString();
	       }  
	       else {
	         reader.skipValue();
	       }
	     }
	     reader.endObject();
	     IASKcontragent result = new IASKcontragent();
	     result.setId(id);
	     result.setName(name);
	     result.setCountryID(countryID);
	     result.setAddress(address);
	     result.setEdrpou(edrpou);
	     result.setNds_certificate(nds_certificate);

	     return result;
		}

	public static IASKimporterCertificate readFieldIC(JsonReader reader) throws IOException {
		Long id = null;
		Long importerID = null;
		String certNum = null;
		Long certRegDate = null;
		String nationalRecNum = null;
		Long nationalRecDate = null;
		Long nationalRecValid = null;

	     reader.beginObject();
	     while (reader.hasNext()) {
	       String jname = reader.nextName();
	       if (jname.equals("id")) {
	         id = reader.nextLong();
	       } else if (jname.equals("importerID")) {
	    	   importerID = reader.nextLong();
	       }  else if (jname.equals("certNum")) {
	    	   certNum = reader.nextString();
	       }  else if (jname.equals("certRegDate")) {
	    	   certRegDate = reader.nextLong();
	       }  else if (jname.equals("nationalRecNum")) {
	    	   nationalRecNum = reader.nextString();
	       }  else if (jname.equals("nationalRecDate")) {
	    	   nationalRecDate = reader.nextLong();
	       }  else if (jname.equals("nationalRecValid")) {
	    	   nationalRecValid = reader.nextLong();
	       }
	       else {
	         reader.skipValue();
	       }
	     }
	     reader.endObject();
	     IASKimporterCertificate result = new IASKimporterCertificate();
	     result.setId(id);
	     result.setImporterID(importerID);
	     result.setCertNum(certNum);
	     result.setCertRegDate(new Date(certRegDate));
	     result.setNationalRecNum(nationalRecNum);
	     result.setNationalRecDate(new Date(nationalRecDate));
	     result.setNationalRecValid(new Date(nationalRecValid));

	     return result;
		}
	
	public static IASKconsigment readFieldConsig(JsonReader reader) throws IOException {
		Long id = null;
		String exporterNumber = null;
		Long uid = null;
		
	     reader.beginObject();
	     while (reader.hasNext()) {
	       String jname = reader.nextName();
	       if (jname.equals("id")) {
	         id = reader.nextLong();
	       } else if (jname.equals("exporterNumber")) {
	    	   exporterNumber = reader.nextString();
	       } 
	       else if (jname.equals("uid")) {
		         uid = reader.nextLong();
		   }
	       else {
	         reader.skipValue();
	       }
	     }
	     reader.endObject();
	     IASKconsigment result = new IASKconsigment();
	     //Date date2 = new Date(date);
	     result.setId(id);
	     result.setExporterNumber(exporterNumber);
	     result.setUid(uid);
	     
	     
	     //System.out.println("id: " + id + " city: " + city + " date: "+ date + "(date2) " + date2 +  " location: "+ location);
	     return result;
		}

	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static IASKpreporateGroup getPGdata(TextView tvResultData, DefaultHttpClient httpClient, int id) {
		String idPG = String.valueOf(id);
		IASKpreporateGroup prepGroup = null;
		
		String secureURLpg = hostAddr + getPGidURL + idPG;
		String nonSecureURL = secureURLpg;
		tvResultData.setText(secureURLpg);
		try {
			//tvResultData.setText("auth");
			URI uri;
	        InputStream data = null;
	        HttpGet httpGet = new HttpGet(nonSecureURL);
	        //tvResultData.setText("httpGet");
	        HttpResponse response;
	        //tvResultData.setText("responce");
	        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
		
			response = httpClient.execute(httpGet);
			//tvResultData.setText("execute");
			//System.out.println(response.getStatusLine().getStatusCode());
			String status = response.getStatusLine().toString();
	        //tvResultData.setText(status);
	        
	        if (response.getStatusLine().getStatusCode() == 200) {
	        	//String body = handler.handleResponse(response);
	            //int code = response.getStatusLine().getStatusCode();
	                 HttpEntity entity = response.getEntity();
	            	// All the work is done for you here :)
	    		     String jsonContent = EntityUtils.toString(entity);
	    		     String except = "\"exception\":";
	    		     //String except2 = "\"exception\":\"Invalid authentication token";
	    		     Pattern pattern = Pattern.compile(except);
	    		     //Pattern pattern2 = Pattern.compile(except2);
	    		     Matcher matcher = pattern.matcher(jsonContent);
	    		     //Matcher matcher2 = pattern2.matcher(jsonContent);
					 if (matcher.find()){
						 Log.d("PREP-GRP", "ERROR! " + jsonContent);
						 return null;
					 }
					 else {
						
						// Create a Reader from String
		    		     Reader stringReader = new StringReader(jsonContent);
		    		
		    		     // Pass the string reader to JsonReader constructor
		    		     JsonReader reader = new JsonReader(stringReader);
		    		     //reader.beginArray();
		    		     //while (reader.hasNext()){		    	 
		    		    	 //listParam.add(readFieldPG(reader));
		    		     //}
		    		     //reader.endArray();
		    		     prepGroup =readFieldPG(reader);
		    		     //Log.d("PREP-GRP", prepGroup.getConsigmentID().toString());
		    		     
						 tvResultData.setText("getData...." + nonSecureURL);
					 }
	        }
	        else {
	        	System.out.println(response.getStatusLine());
	        }
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Log.d("HTTPCLIENT", e.getLocalizedMessage());
		}
		return prepGroup;
	}
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static IASKpreporate getPrepData(DefaultHttpClient httpClient, Long id) {
		String idPrep = String.valueOf(id);
		IASKpreporate preporate = null;
		
		String secureURLpg = hostAddr + getPrepIdURL + idPrep;
		String nonSecureURL = secureURLpg;
		try {
			
			
			//tvResultData.setText("auth");
			URI uri;
	        InputStream data = null;
	        HttpGet httpGet = new HttpGet(nonSecureURL);
	        //tvResultData.setText("httpGet");
	        HttpResponse response;
	        //tvResultData.setText("responce");
	        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
		
			response = httpClient.execute(httpGet);
			//tvResultData.setText("execute");
			System.out.println(response.getStatusLine().getStatusCode());
			String status = response.getStatusLine().toString();
	        //tvResultData.setText(status);
	        
	        if (response.getStatusLine().getStatusCode() == 200) {
	        	HttpEntity entity = response.getEntity();
	            	// All the work is done for you here :)
	        	String jsonContent = EntityUtils.toString(entity);
	    		     // Create a Reader from String
	        	Reader stringReader = new StringReader(jsonContent);
	    		
	    		 // Pass the string reader to JsonReader constructor
	        	JsonReader reader = new JsonReader(stringReader);
	    		preporate =readFieldPrep(reader);
	    		
	    		     
	        }
	        else {
	        	System.out.println(response.getStatusLine());
	        }
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("HTTPCLIENT", e.getLocalizedMessage());
		}
		return preporate;
	}
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static IASKactiveSubstance getASdata(TextView tvResultData, DefaultHttpClient httpClient, Long id) {
		String idPrep = String.valueOf(id);
		IASKactiveSubstance result = null;
		String secureURLpg = hostAddr + getASidURL + idPrep;
		String nonSecureURL = secureURLpg;
		tvResultData.setText(secureURLpg);
		try {
			URI uri;
	        InputStream data = null;
	        HttpGet httpGet = new HttpGet(nonSecureURL);
	        HttpResponse response;
	        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
		
			response = httpClient.execute(httpGet);
			System.out.println(response.getStatusLine().getStatusCode());
			String status = response.getStatusLine().toString();
	        
	        if (response.getStatusLine().getStatusCode() == 200) {
	        	HttpEntity entity = response.getEntity();
	        	String jsonContent = EntityUtils.toString(entity);
	        	Reader stringReader = new StringReader(jsonContent);
	        	JsonReader reader = new JsonReader(stringReader);
	        	result =readFieldAS(reader);     
	        }
	        else {
	        	System.out.println(response.getStatusLine());
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("HTTPCLIENT", e.getLocalizedMessage());
		}
		return result;
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static IASKcontainer getContainerData(TextView tvResultData, DefaultHttpClient httpClient, Long id) {
		String idPrep = String.valueOf(id);
		IASKcontainer result = null;
		String secureURLpg = hostAddr + getContainerIdURL + idPrep;
		String nonSecureURL = secureURLpg;
		tvResultData.setText(secureURLpg);
		try {
			URI uri;
	        InputStream data = null;
	        HttpGet httpGet = new HttpGet(nonSecureURL);
	        HttpResponse response;
	        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
		
			response = httpClient.execute(httpGet);
			System.out.println(response.getStatusLine().getStatusCode());
			String status = response.getStatusLine().toString();
	        
	        if (response.getStatusLine().getStatusCode() == 200) {
	        	HttpEntity entity = response.getEntity();
	        	String jsonContent = EntityUtils.toString(entity);
	        	Reader stringReader = new StringReader(jsonContent);
	        	JsonReader reader = new JsonReader(stringReader);
	        	result =readFieldContainer(reader);     
	        }
	        else {
	        	System.out.println(response.getStatusLine());
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("HTTPCLIENT", e.getLocalizedMessage());
		}
		return result;
	}
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static IASKcontainerType getCTData(TextView tvResultData, DefaultHttpClient httpClient, Long id) {
		String idPrep = String.valueOf(id);
		IASKcontainerType result = null;
		String secureURLpg = hostAddr + getCTidURL + idPrep;
		String nonSecureURL = secureURLpg;
		tvResultData.setText(secureURLpg);
		try {
			URI uri;
	        InputStream data = null;
	        HttpGet httpGet = new HttpGet(nonSecureURL);
	        HttpResponse response;
	        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
		
			response = httpClient.execute(httpGet);
			System.out.println(response.getStatusLine().getStatusCode());
			String status = response.getStatusLine().toString();
	        
	        if (response.getStatusLine().getStatusCode() == 200) {
	        	HttpEntity entity = response.getEntity();
	        	String jsonContent = EntityUtils.toString(entity);
	        	Reader stringReader = new StringReader(jsonContent);
	        	JsonReader reader = new JsonReader(stringReader);
	        	result =readFieldCT(reader);     
	        }
	        else {
	        	System.out.println(response.getStatusLine());
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("HTTPCLIENT", e.getLocalizedMessage());
		}
		return result;
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static IASKcontainerVolume getCVData(TextView tvResultData, DefaultHttpClient httpClient, Long id) {
		String idPrep = String.valueOf(id);
		IASKcontainerVolume result = null;
		String secureURLpg = hostAddr + getCVidURL + idPrep;
		String nonSecureURL = secureURLpg;
		tvResultData.setText(secureURLpg);
		try {
			URI uri;
	        InputStream data = null;
	        HttpGet httpGet = new HttpGet(nonSecureURL);
	        HttpResponse response;
	        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
		
			response = httpClient.execute(httpGet);
			System.out.println(response.getStatusLine().getStatusCode());
			String status = response.getStatusLine().toString();
	        
	        if (response.getStatusLine().getStatusCode() == 200) {
	        	HttpEntity entity = response.getEntity();
	        	String jsonContent = EntityUtils.toString(entity);
	        	Reader stringReader = new StringReader(jsonContent);
	        	JsonReader reader = new JsonReader(stringReader);
	        	result =readFieldCV(reader);     
	        }
	        else {
	        	System.out.println(response.getStatusLine());
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("HTTPCLIENT", e.getLocalizedMessage());
		}
		return result;
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static IASKcontragent getCAData(TextView tvResultData, DefaultHttpClient httpClient, Long id) {
		String idPrep = String.valueOf(id);
		IASKcontragent result = null;
		String secureURLpg = hostAddr + getCAidURL + idPrep;
		String nonSecureURL = secureURLpg;
		tvResultData.setText(secureURLpg);
		try {
			URI uri;
	        InputStream data = null;
	        HttpGet httpGet = new HttpGet(nonSecureURL);
	        HttpResponse response;
	        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
		
			response = httpClient.execute(httpGet);
			System.out.println(response.getStatusLine().getStatusCode());
			String status = response.getStatusLine().toString();
	        
	        if (response.getStatusLine().getStatusCode() == 200) {
	        	HttpEntity entity = response.getEntity();
	        	String jsonContent = EntityUtils.toString(entity);
	        	Reader stringReader = new StringReader(jsonContent);
	        	JsonReader reader = new JsonReader(stringReader);
	        	result =readFieldCA(reader);     
	        }
	        else {
	        	System.out.println(response.getStatusLine());
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("HTTPCLIENT", e.getLocalizedMessage());
		}
		return result;
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static IASKimporterCertificate getICData(TextView tvResultData, DefaultHttpClient httpClient, Long id) {
		String idPrep = String.valueOf(id);
		IASKimporterCertificate result = null;
		String secureURLpg = hostAddr + getICidURL + idPrep;
		String nonSecureURL = secureURLpg;
		tvResultData.setText(secureURLpg);
		try {
			URI uri;
	        InputStream data = null;
	        HttpGet httpGet = new HttpGet(nonSecureURL);
	        HttpResponse response;
	        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
		
			response = httpClient.execute(httpGet);
			System.out.println(response.getStatusLine().getStatusCode());
			String status = response.getStatusLine().toString();
	        
	        if (response.getStatusLine().getStatusCode() == 200) {
	        	HttpEntity entity = response.getEntity();
	        	String jsonContent = EntityUtils.toString(entity);
	        	Reader stringReader = new StringReader(jsonContent);
	        	JsonReader reader = new JsonReader(stringReader);
	        	result =readFieldIC(reader);     
	        }
	        else {
	        	System.out.println(response.getStatusLine());
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("HTTPCLIENT", e.getLocalizedMessage());
		}
		return result;
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static IASKconsigment getConsigData(TextView tvResultData, DefaultHttpClient httpClient, Long id) {
		String idPrep = String.valueOf(id);
		IASKconsigment result = null;
		String secureURLpg = hostAddr + getConsigIdURL + idPrep;
		String nonSecureURL = secureURLpg;
		tvResultData.setText(secureURLpg);
		try {
			URI uri;
	        InputStream data = null;
	        HttpGet httpGet = new HttpGet(nonSecureURL);
	        HttpResponse response;
	        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
		
			response = httpClient.execute(httpGet);
			System.out.println(response.getStatusLine().getStatusCode());
			String status = response.getStatusLine().toString();
	        
	        if (response.getStatusLine().getStatusCode() == 200) {
	        	HttpEntity entity = response.getEntity();
	        	String jsonContent = EntityUtils.toString(entity);
	        	Reader stringReader = new StringReader(jsonContent);
	        	JsonReader reader = new JsonReader(stringReader);
	        	result =readFieldConsig(reader);     
	        }
	        else {
	        	System.out.println(response.getStatusLine());
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("HTTPCLIENT", e.getLocalizedMessage());
		}
		return result;
	}

}

