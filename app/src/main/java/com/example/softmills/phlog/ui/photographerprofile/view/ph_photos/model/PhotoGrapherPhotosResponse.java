package com.example.softmills.phlog.ui.photographerprofile.view.ph_photos.model;

import com.example.softmills.phlog.ui.photographerprofile.view.ph_saved.model.SavedPhotosData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 10/1/2018.
 */
public class PhotoGrapherPhotosResponse {

    @SerializedName("data")
    @Expose
    public PhotosData data;
    @SerializedName("state")
    @Expose
    public String state;
}
