package com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.brand.model;

 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 11/11/2018.
 */
public class PhotographerFollowingBrandResponse {
    @SerializedName("data")
    @Expose
    public PhotographerFollowingBrandData data;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("state")
    @Expose
    public String state;
}
