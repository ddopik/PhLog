package com.example.softmills.phlog.ui.earning.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EarningDetailsResponse {
    @SerializedName("data")
    @Expose
    public Earning data;

}