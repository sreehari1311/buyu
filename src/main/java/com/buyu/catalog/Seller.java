package com.buyu.catalog;

public class Seller {

	private String sellerId;
	private String subProductId;
	private float mrp;
	private float sellingPrice;
	private float discount;
	private String coupon;
	private String sellerProductId;
	private String sellerProductURL;
	public String getSellerProductId() {
		return sellerProductId;
	}
	public void setSellerProductId(String sellerProductId) {
		this.sellerProductId = sellerProductId;
	}
	public String getSellerProductURL() {
		return sellerProductURL;
	}
	public void setSellerProductURL(String sellerProductURL) {
		this.sellerProductURL = sellerProductURL;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getSubProductId() {
		return subProductId;
	}
	public void setSubProductId(String subProductId) {
		this.subProductId = subProductId;
	}
	public float getMrp() {
		return mrp;
	}
	public void setMrp(float mrp) {
		this.mrp = mrp;
	}
	public float getSellingPrice() {
		return sellingPrice;
	}
	public void setSellingPrice(float sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	public String getCoupon() {
		return coupon;
	}
	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}
}
