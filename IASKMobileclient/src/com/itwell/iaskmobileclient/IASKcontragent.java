package com.itwell.iaskmobileclient;

public class IASKcontragent {
	private Long id;
	private String name;
	private Long countryID;
	private String address;
	private String edrpou;
	private String nds_certificate;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Long getCountryID() {
		return countryID;
	}
	public void setCountryID(Long countryID) {
		this.countryID = countryID;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getEdrpou() {
		return edrpou;
	}
	public void setEdrpou(String edrpou) {
		this.edrpou = edrpou;
	}
	
	public String getNds_certificate() {
		return nds_certificate;
	}
	public void setNds_certificate(String nds_certificate) {
		this.nds_certificate = nds_certificate;
	}
}
