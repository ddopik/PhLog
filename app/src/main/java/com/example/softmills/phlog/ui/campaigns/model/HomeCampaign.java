package com.example.softmills.phlog.ui.campaigns.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abdalla_maged on 10/2/2018.
 */
public class HomeCampaign {


    @SerializedName("end_date")
    @Expose
    public String endDate;
    @SerializedName("descrption_en")
    @Expose
    public String descrptionEn;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("number_images")
    @Expose
    public String numberImages;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("title_en")
    @Expose
    public String titleEn;
    @SerializedName("prize")
    @Expose
    public String prize;
    @SerializedName("business_id")
    @Expose
    public String businessId;
    @SerializedName("start_date")
    @Expose
    public String startDate;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("campaign_tags")
    @Expose
    public List<CampaignTag> campaignTags = null;
    @SerializedName("publish")
    @Expose
    public String publish;
    @SerializedName("no_winners")
    @Expose
    public String noWinners;



}
