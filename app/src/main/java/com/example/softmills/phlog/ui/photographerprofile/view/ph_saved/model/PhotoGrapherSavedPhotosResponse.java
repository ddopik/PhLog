package com.example.softmills.phlog.ui.photographerprofile.view.ph_saved.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 9/30/2018.
 */
public class PhotoGrapherSavedPhotosResponse {
    @SerializedName("data")
    @Expose
    public SavedPhotosData data;

}
