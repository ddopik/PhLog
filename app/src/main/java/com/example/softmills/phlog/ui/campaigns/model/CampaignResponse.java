package com.example.softmills.phlog.ui.campaigns.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abdalla_maged on 10/2/2018.
 */
public class CampaignResponse {

        @SerializedName("data")
        @Expose
        public CampaignData data;
}
