package com.itwell.iaskmobileclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;
import android.os.StrictMode.*;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.stream.JsonReader;

public class IASKuniqID {
	private int prepGroupID;
	private IASKpreporateGroup pgResult;
	private IASKconsigment consigment;
	private IASKpreporate preporate;
	private IASKactiveSubstance activeSubs;
	private IASKcontainer container;
	private IASKcontainerType contType;
	private IASKcontainerVolume contVolume;
	private IASKcontragent prepMaker;
	private IASKcontragent asMaker;
	private IASKcontragent importer;
	private IASKimporterCertificate certImport;
	private DefaultHttpClient httpClient;
	private static boolean isNormal;
	private static String hostAddr;
	private static String getPGidURL = "/iaskdbport-portlet/api/secure/jsonws/iaskpreporategroup/get-ias-kpreporate-group-by-id/id/";
	private static String getPrepIdURL = "/iaskdbport-portlet/api/secure/jsonws/iaskpreporate/get-ias-kpreporate-by-id/id/";
	private static String getASidURL = "/iaskdbport-portlet/api/secure/jsonws/iaskactivesubstance/get-ias-kactive-substance-by-id/id/";
	private static String getContainerIdURL = "/iaskdbport-portlet/api/secure/jsonws/iaskcontainer/get-ias-kcontainer-by-id/id/";
	private static String getCTidURL = "/iaskdbport-portlet/api/secure/jsonws/iaskcontainertype/get-ias-kcontainer-type-by-id/id/";
	private static String getCVidURL = "/iaskdbport-portlet/api/secure/jsonws/iaskcontainervolume/get-ias-kcontainer-volume-by-id/id/";
	private static String getCAidURL = "/iaskdbport-portlet/api/secure/jsonws/iaskcontragent/get-ias-kcontragents-by-id/id/";
	private static String getICidURL = "/iaskdbport-portlet/api/secure/jsonws/iaskimportercertificate/get-ias-kimporter-certificate-by-id/id/";
	private static String getConsigIdURL = "/iaskdbport-portlet/api/secure/jsonws/iaskconsigment/get-ias-kconsigment-by-id/id/";

	public IASKuniqID() {
		this.pgResult=null;
		this.preporate=null;
		this.activeSubs=null;
		this.container=null;
		this.contType=null;
		this.contVolume=null;
		this.prepMaker=null;
		this.asMaker=null;
		this.importer=null;
		this.certImport=null;
		this.httpClient=null;
		prepGroupID = 0;
		isNormal = false;
		//getAllData();
	}
	
	public IASKuniqID(DefaultHttpClient httpClient) {
		this.pgResult=null;
		this.preporate=null;
		this.activeSubs=null;
		this.container=null;
		this.contType=null;
		this.contVolume=null;
		this.prepMaker=null;
		this.asMaker=null;
		this.importer=null;
		this.certImport=null;
		this.httpClient=httpClient;
		prepGroupID = 0;
		isNormal = false;
		//getAllData();
	}
	public IASKuniqID(DefaultHttpClient httpClient, String hostAddr) {
		this.pgResult=null;
		this.preporate=null;
		this.activeSubs=null;
		this.container=null;
		this.contType=null;
		this.contVolume=null;
		this.prepMaker=null;
		this.asMaker=null;
		this.importer=null;
		this.certImport=null;
		this.httpClient=httpClient;
		this.hostAddr = hostAddr;
		prepGroupID = 0;
		isNormal = false;
		//getAllData();
	}
	public IASKuniqID(DefaultHttpClient httpClient, String hostAddr, int prepGroupID) {
		this.pgResult=null;
		this.preporate=null;
		this.activeSubs=null;
		this.container=null;
		this.contType=null;
		this.contVolume=null;
		this.prepMaker=null;
		this.asMaker=null;
		this.importer=null;
		this.certImport=null;
		this.httpClient=httpClient;
		this.hostAddr = hostAddr;
		this.prepGroupID = prepGroupID;
		this.isNormal = false;
		//getAllData();
	}

	// 
	public boolean resultOK() {
		if (isNormal==true){
			Log.d("resultOk", "TRUE");
		}
		else {
			Log.d("resultOk", "FALSE");
		}
		return isNormal;
	}
	
	public int getPrepGroupID() {
		return prepGroupID;
	}
	public void setPrepGroupID(int prepGroupID) {
		this.prepGroupID = prepGroupID;
	}
	
	public String getHostAddr() {
		return hostAddr;
	}
	public void setHostAddr(String hostAddr) {
		this.hostAddr = hostAddr;
	}
	
	public IASKpreporateGroup getPgResult(){
		return pgResult;	
	}
	public void setPgResult(IASKpreporateGroup pgResult) {
		this.pgResult = pgResult;
	}
	/*
	public int getPort() {
		return port;
	}
	public void setPort(int port ) {
		this.port = port;
	}
	
	public String getProto() {
		return proto;
	}
	public void set(String  proto ) {
		this.proto = proto;
	}
	*/
	public DefaultHttpClient getHttpClient() {
		return httpClient;
	}
	public void setHttpClient(DefaultHttpClient httpClient) {
		this.httpClient = httpClient;
	}
	
	public IASKpreporate getPreporate() {
		return preporate;
	}
	public void setPrepotate(IASKpreporate preporate) {
		this.preporate = preporate;
	}
	
	public IASKactiveSubstance getActiveSubs() {
		return activeSubs;
	}
	public void setActiveSubs(IASKactiveSubstance actveSubs) {
		this.activeSubs = actveSubs;
	}
	
	public IASKcontainer getContainer() {
		return container;
	}
	public void set(IASKcontainer container) {
		this.container = container;
	}
	
	public IASKcontainerType getContainerType() {
		return contType;
	}
	public void setContainerType(IASKcontainerType contType ) {
		this.contType =contType;
	}
	
	public IASKcontainerVolume getContainerVolume() {
		return contVolume;
	}
	public void setContainerVolume(IASKcontainerVolume contVolume) {
		this.contVolume = contVolume;
	}
	
	public IASKcontragent getAsMaker() {
		return asMaker;
	}
	public void setAsMaker(IASKcontragent asMaker) {
		this.asMaker = asMaker;
	}
	
	public IASKcontragent getPrepMaker() {
		return prepMaker;
	}
	public void setPrepMaker(IASKcontragent prepMaker) {
		this.prepMaker = prepMaker;
	}
	
	public IASKcontragent getImporter() {
		return importer;
	}
	public void setImporter(IASKcontragent importer) {
		this.importer = importer;
	}
	
	public IASKimporterCertificate getCertImporter() {
		return certImport;
	}
	public void setCertImporter(IASKimporterCertificate certImport ) {
		this.certImport = certImport;
	}
	
	public IASKconsigment getCosigment() {
		return consigment;
	}
	public void setConsigment(IASKconsigment consigment ) {
		this.consigment = consigment;
	}
	
	public void getAllData() {
		Log.d("IASKuniqID", "getPGdata(httpClient, prepGroupID");
		pgResult = getPGdata(httpClient, prepGroupID);
		if (pgResult!=null){
			Log.d("IASKuniqID", "pgResult!=null");
			isNormal=true;
			if (pgResult.getConsigmentID()!=null){
				consigment = getConsigData(httpClient, pgResult.getConsigmentID());
				Log.d("IASKuniqID", "consigment = getConsigData(httpClient, pgResult.getConsigmentID())");
				Log.d("IASKuniqID", String.valueOf(pgResult.getConsigmentID()));
			}
			else {
				consigment=null;
				Log.d("IASKuniqID", "consigment = null");
			}
			if (pgResult.getPreporateNameID()!=null){
				preporate = getPrepData(httpClient, pgResult.getPreporateNameID());
				Log.d("IASKuniqID", "preporate = getPrepData(httpClient, pgResult.getPreporateNameID())");
				Log.d("IASKuniqID", pgResult.getPreporateNameID().toString());
				if (preporate.getActiveSubstanceID()!=null){
					activeSubs = getASdata(httpClient,preporate.getActiveSubstanceID());
					Log.d("IASKuniqID", "activeSubs = getASdata(httpClient,preporate.getActiveSubstanceID())");
					Log.d("IASKuniqID", preporate.getActiveSubstanceID().toString());
				}
				else {
					activeSubs=null;
					Log.d("IASKuniqID", "activeSubs = null");
				}
			}
			else {
				preporate = null;
				Log.d("IASKuniqID", "preporate = null");
			}
	    	if (pgResult.getContainerID()!=null){
	    		container = getContainerData(httpClient, pgResult.getContainerID());
	    		Log.d("IASKuniqID", "getContainerData(httpClient, pgResult.getContainerID()");
	    		Log.d("IASKuniqID", pgResult.getContainerID().toString());
	    		if (container.getMaterialID()!=null){
	    			contType = getCTData(httpClient, container.getMaterialID());
	    			Log.d("IASKuniqID", "contType = getCTData(httpClient, container.getMaterialID())");
	    			Log.d("IASKuniqID", container.getMaterialID().toString());
	    		}
	    		else {
	    			contType=null;
	    			Log.d("IASKuniqID", "contType = null");
	    		}
	    		if (container.getVolumeID()!=null){
	    			contVolume = getCVData(httpClient, container.getVolumeID());
	    			Log.d("IASKuniqID", "contVolume = getCVData(httpClient, container.getVolumeID())");
	    			Log.d("IASKuniqID", container.getVolumeID().toString());
	    		}
	    		else {
	    			contVolume=null;
	    			Log.d("IASKuniqID", "contVolume = null");
	    		}
	    	}
	    	else {
	    		container=null;
	    		contType=null;
	    		contVolume=null;
	    		Log.d("IASKuniqID", "container/contType/contVolume = null");
	    	}
	    	
	    	if (pgResult.getMakerPrepID()!=null){
	    		prepMaker = getCAData(httpClient, pgResult.getMakerPrepID());
	    		Log.d("IASKuniqID", "prepMaker = getCAData(httpClient, pgResult.getMakerPrepID())");
	    		Log.d("IASKuniqID", pgResult.getMakerPrepID().toString());
	    	}
	    	else {
	    		prepMaker =null;
	    		Log.d("IASKuniqID", "prepMaker = null");
	    	}
	    	if (pgResult.getMakerActiveSubsID()!=null){
	    		asMaker = getCAData(httpClient, pgResult.getMakerActiveSubsID());
	    		Log.d("IASKuniqID", "asMaker = getCAData(httpClient, pgResult.getMakerActiveSubsID())");
	    		Log.d("IASKuniqID", pgResult.getMakerActiveSubsID().toString());
	    	}
	    	else {
	    		asMaker=null;
	    		Log.d("IASKuniqID", "asMaker = null");
	    	}
	    	if (pgResult.getImporterID()!=null){
	    		importer = getCAData(httpClient, pgResult.getImporterID());
	    		Log.d("IASKuniqID", "importer = getCAData(httpClient, pgResult.getImporterID())");
	    		Log.d("IASKuniqID", pgResult.getImporterID().toString());
	    	}
	    	else {
	    		importer=null;
	    		Log.d("IASKuniqID", "importer = null");
	    	}
	    	if (pgResult.getImporterCertificateID()!=null){
	    		certImport = getICData(httpClient, pgResult.getImporterCertificateID());
	    		Log.d("IASKuniqID", "certImport = getICData(httpClient, pgResult.getImporterCertificateID())");
	    		Log.d("IASKuniqID", pgResult.getImporterCertificateID().toString());
	    	}
	    	else {
	    		certImport=null;
	    		Log.d("IASKuniqID", "certImport = null");
	    	}
		}else {
			isNormal=false;
			Log.d("IASKuniqID", "isNormal=false");
		}
	}
	
	private static IASKpreporateGroup readFieldPG(JsonReader reader) throws IOException {
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

	private static IASKpreporate readFieldPrep(JsonReader reader) throws IOException {
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

	private static IASKactiveSubstance readFieldAS(JsonReader reader) throws IOException {
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

	private static IASKcontainer readFieldContainer(JsonReader reader) throws IOException {
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

	private static IASKcontainerType readFieldCT(JsonReader reader) throws IOException {
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

	private static IASKcontainerVolume readFieldCV(JsonReader reader) throws IOException {
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

	private static IASKcontragent readFieldCA(JsonReader reader) throws IOException {
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

	private static IASKimporterCertificate readFieldIC(JsonReader reader) throws IOException {
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
	
	private static IASKconsigment readFieldConsig(JsonReader reader) throws IOException {
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
	private static IASKpreporateGroup getPGdata(DefaultHttpClient httpClient, int id) {
		String idPG = String.valueOf(id);
		IASKpreporateGroup prepGroup = null;
		
		String secureURLpg = hostAddr + getPGidURL + idPG;
		String nonSecureURL = secureURLpg;
		try {
			//tvResultData.setText("auth");
			URI uri;
	        InputStream data = null;
	        HttpGet httpGet = new HttpGet(nonSecureURL);
	        //tvResultData.setText("httpGet");
	        HttpResponse response;
	        //tvResultData.setText("responce");
	        //Log.d("STRICT", "Try to strict :)");
	        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        //Log.d("STRICT", "OK!");
	        //StrictMode.setThreadPolicy(policy);
		
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
	private static IASKpreporate getPrepData(DefaultHttpClient httpClient, Long id) {
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
	        if (Build.VERSION.SDK_INT> Build.VERSION_CODES.FROYO) {
	        	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		        StrictMode.setThreadPolicy(policy);
	        }
	        
		
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
	private static IASKactiveSubstance getASdata(DefaultHttpClient httpClient, Long id) {
		String idPrep = String.valueOf(id);
		IASKactiveSubstance result = null;
		String secureURLpg = hostAddr + getASidURL + idPrep;
		String nonSecureURL = secureURLpg;
		try {
			URI uri;
	        InputStream data = null;
	        HttpGet httpGet = new HttpGet(nonSecureURL);
	        HttpResponse response;
	        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        //StrictMode.setThreadPolicy(policy);
		
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
	private static IASKcontainer getContainerData(DefaultHttpClient httpClient, Long id) {
		String idPrep = String.valueOf(id);
		IASKcontainer result = null;
		String secureURLpg = hostAddr + getContainerIdURL + idPrep;
		String nonSecureURL = secureURLpg;
		try {
			URI uri;
	        InputStream data = null;
	        HttpGet httpGet = new HttpGet(nonSecureURL);
	        HttpResponse response;
	        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        //StrictMode.setThreadPolicy(policy);
		
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
	private static IASKcontainerType getCTData(DefaultHttpClient httpClient, Long id) {
		String idPrep = String.valueOf(id);
		IASKcontainerType result = null;
		String secureURLpg = hostAddr + getCTidURL + idPrep;
		String nonSecureURL = secureURLpg;
		try {
			URI uri;
	        InputStream data = null;
	        HttpGet httpGet = new HttpGet(nonSecureURL);
	        HttpResponse response;
	        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        //StrictMode.setThreadPolicy(policy);
		
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
	private static IASKcontainerVolume getCVData(DefaultHttpClient httpClient, Long id) {
		String idPrep = String.valueOf(id);
		IASKcontainerVolume result = null;
		String secureURLpg = hostAddr + getCVidURL + idPrep;
		String nonSecureURL = secureURLpg;
		try {
			URI uri;
	        InputStream data = null;
	        HttpGet httpGet = new HttpGet(nonSecureURL);
	        HttpResponse response;
	        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        //StrictMode.setThreadPolicy(policy);
		
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
	private static IASKcontragent getCAData(DefaultHttpClient httpClient, Long id) {
		String idPrep = String.valueOf(id);
		IASKcontragent result = null;
		String secureURLpg = hostAddr + getCAidURL + idPrep;
		String nonSecureURL = secureURLpg;
		try {
			URI uri;
	        InputStream data = null;
	        HttpGet httpGet = new HttpGet(nonSecureURL);
	        HttpResponse response;
	        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        //StrictMode.setThreadPolicy(policy);
		
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
	private static IASKimporterCertificate getICData(DefaultHttpClient httpClient, Long id) {
		String idPrep = String.valueOf(id);
		IASKimporterCertificate result = null;
		String secureURLpg = hostAddr + getICidURL + idPrep;
		String nonSecureURL = secureURLpg;
		try {
			URI uri;
	        InputStream data = null;
	        HttpGet httpGet = new HttpGet(nonSecureURL);
	        HttpResponse response;
	        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        //StrictMode.setThreadPolicy(policy);
		
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
	private static IASKconsigment getConsigData(DefaultHttpClient httpClient, Long id) {
		String idPrep = String.valueOf(id);
		IASKconsigment result = null;
		String secureURLpg = hostAddr + getConsigIdURL + idPrep;
		String nonSecureURL = secureURLpg;
		try {
			URI uri;
	        InputStream data = null;
	        HttpGet httpGet = new HttpGet(nonSecureURL);
	        HttpResponse response;
	        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        //StrictMode.setThreadPolicy(policy);
		
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
