package com.buyu.flipkart;

public class FlipKart {

	private long productId;
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public String getFlipKartId() {
		return flipKartId;
	}
	public void setFlipKartId(String flipKartId) {
		this.flipKartId = flipKartId;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getProductURL() {
		return productURL;
	}
	public void setProductURL(String productURL) {
		this.productURL = productURL;
	}
	private String flipKartId;
	private float price;
	private String date;
	private String productURL;
	
	
}
