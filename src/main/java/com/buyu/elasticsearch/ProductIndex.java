package com.buyu.elasticsearch;

public class ProductIndex {

	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getInternalMemory() {
		return internalMemory;
	}
	public void setInternalMemory(String internalMemory) {
		this.internalMemory = internalMemory;
	}
	public String getInternalMemoryUnit() {
		return internalMemoryUnit;
	}
	public void setInternalMemoryUnit(String internalMemoryUnit) {
		this.internalMemoryUnit = internalMemoryUnit;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getSubProductId() {
		return subProductId;
	}
	public void setSubProductId(String subProductId) {
		this.subProductId = subProductId;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	private String productName;
	private String color;
	private String internalMemory;
	private String internalMemoryUnit;
	private String productId;
	private String subProductId;
	private String brand;
	private String imageURL;
	
}
