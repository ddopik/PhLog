package com.example.softmills.phlog.ui.earning.model;


import com.google.gson.annotations.SerializedName;

public class EarningDetailsResponse{

	@SerializedName("data")
	private Earning data;

	public Earning getData() {
		return data;
	}

	public void setData(Earning data) {
		this.data = data;
	}
}