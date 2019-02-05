package com.example.softmills.phlog.ui.earning.model;


import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.example.softmills.phlog.base.commonmodel.Business;
import com.google.gson.annotations.SerializedName;

public class Earning {

	@SerializedName("photographer_level")
	private int photographerLevel;

	@SerializedName("photo_id")
	private int photoId;

	@SerializedName("business")
	private Business business;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("photo")
	private BaseImage photo;

	@SerializedName("deleted_at")
	private Object deletedAt;

	@SerializedName("payment_gateway_trans_id")
	private String paymentGatewayTransId;

	@SerializedName("payment_gateway_trans_response")
	private String paymentGatewayTransResponse;

	@SerializedName("updated_at")
	private Object updatedAt;

	@SerializedName("price")
	private String price;

	@SerializedName("exclusive")
	private int exclusive;

	@SerializedName("id")
	private int id;

	@SerializedName("business_id")
	private int businessId;

	public void setPhotographerLevel(int photographerLevel){
		this.photographerLevel = photographerLevel;
	}

	public int getPhotographerLevel(){
		return photographerLevel;
	}

	public void setPhotoId(int photoId){
		this.photoId = photoId;
	}

	public int getPhotoId(){
		return photoId;
	}

	public void setBusiness(Business business){
		this.business = business;
	}

	public Business getBusiness(){
		return business;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setDeletedAt(Object deletedAt){
		this.deletedAt = deletedAt;
	}

	public Object getDeletedAt(){
		return deletedAt;
	}

	public void setPaymentGatewayTransId(String paymentGatewayTransId){
		this.paymentGatewayTransId = paymentGatewayTransId;
	}

	public String getPaymentGatewayTransId(){
		return paymentGatewayTransId;
	}

	public void setPaymentGatewayTransResponse(String paymentGatewayTransResponse){
		this.paymentGatewayTransResponse = paymentGatewayTransResponse;
	}

	public String getPaymentGatewayTransResponse(){
		return paymentGatewayTransResponse;
	}

	public void setUpdatedAt(Object updatedAt){
		this.updatedAt = updatedAt;
	}

	public Object getUpdatedAt(){
		return updatedAt;
	}

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
	}

	public void setExclusive(int exclusive){
		this.exclusive = exclusive;
	}

	public int getExclusive(){
		return exclusive;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setBusinessId(int businessId){
		this.businessId = businessId;
	}

	public int getBusinessId(){
		return businessId;
	}

	public BaseImage getPhoto() {
		return photo;
	}

	public void setPhoto(BaseImage photo) {
		this.photo = photo;
	}
}