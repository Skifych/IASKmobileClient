package com.itwell.iaskmobileclient;

public class IASKbarcode {
	private Long id;
	private String barcodeGroup;
	private Long preporateGroupID;
	private Long consigmentID;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getBarcodeGroup() {
		return barcodeGroup;
	}
	public void setBarcodeGroup(String barcodeGroup) {
		this.barcodeGroup = barcodeGroup;
	}
	
	public Long getPreporateGroupID() {
		return preporateGroupID;
	}
	public void setPreporateGroupID(Long preporateGroupID) {
		this.preporateGroupID = preporateGroupID;
	}
	
	public Long getConsigmentID() {
		return consigmentID;
	}
	public void setConsigmentID(Long consigmentID) {
		this.consigmentID = consigmentID;
	}
}
