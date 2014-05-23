package com.itwell.iaskmobileclient;

import java.util.Date;

public class IASKimporterCertificate {
	private Long id;
	private Long importerID;
	private String certNum;
	private Date certRegDate;
	private String nationalRecNum;
	private Date nationalRecDate;
	private Date nationalRecValid;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getImporterID() {
		return importerID;
	}
	public void setImporterID(Long importerID) {
		this.importerID = importerID;
	}
		
	public String getCertNum() {
		return certNum;
	}
	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}
	
	public Date getCertRegDate() {
		return certRegDate;
	}
	public void setCertRegDate(Date certRegDate) {
		this.certRegDate = certRegDate;
	}
	
	public String getNationalRecNum() {
		return nationalRecNum;
	}
	public void setNationalRecNum(String nationalRecNum) {
		this.nationalRecNum = nationalRecNum;
	}
	
	public Date getNationalRecDate() {
		return nationalRecDate;
	}
	public void setNationalRecDate(Date nationalRecDate) {
		this.nationalRecDate = nationalRecDate;
	}
	

	public Date getNationalRecValid() {
		return nationalRecValid;
	}
	public void setNationalRecValid(Date Valid) {
		this.nationalRecDate = nationalRecValid;
	}
	
	
}
