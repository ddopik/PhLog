package com.example.softmills.phlog.ui.photographerprofile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 10/1/2018.
 */
public class ProfilePhotoGrapherInfoResponse {

    @SerializedName("data")
    @Expose
    public PhotoGrapherProfileData data;
    @SerializedName("state")
    @Expose
    public String state;
}
