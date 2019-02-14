package com.example.softmills.phlog.ui.earning.model;

import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.example.softmills.phlog.base.commonmodel.Business;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EarningListResponse {


	@SerializedName("data")
	@Expose
	public EarningListData data;

}