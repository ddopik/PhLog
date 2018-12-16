package com.example.softmills.phlog.ui.photographerprofile.model;

import com.example.softmills.phlog.base.commonmodel.Photographer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 10/1/2018.
 */
public class ProfilePhotoGrapherInfoResponse {

    @SerializedName("data")
    @Expose
    public Photographer data;
    @SerializedName("state")
    @Expose
    public String state;
}
