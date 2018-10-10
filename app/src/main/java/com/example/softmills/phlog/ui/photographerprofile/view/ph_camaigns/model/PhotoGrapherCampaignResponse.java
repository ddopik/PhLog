package com.example.softmills.phlog.ui.photographerprofile.view.ph_camaigns.model;

import com.example.softmills.phlog.base.BaseApiResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 10/10/2018.
 */
public class PhotoGrapherCampaignResponse extends BaseApiResponse {
    @SerializedName("data")
    @Expose
    public PhotoGrapherJoinedCampaign data;
}
