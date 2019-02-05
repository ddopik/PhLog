package com.example.softmills.phlog.ui.earning.model;

import com.google.gson.annotations.SerializedName;

public class EarningListResponse{

	@SerializedName("data")
	private Data data;

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}
}