package com.itwell.iaskmobileclient;

public class IASKcontainer {
	private Long id;
	private Long materialID;
	private Long volumeID;
	private Double size;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getMaterialID() {
		return materialID;
	}
	public void setMaterialID(Long materialID) {
		this.materialID = materialID;
	}
	public Long getVolumeID() {
		return volumeID;
	}
	public void setVolumeID(Long volumeID) {
		this.volumeID = volumeID;
	}
	
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
		this.size = size;
	}
	
}
