package com.example.softmills.phlog.ui.campaigns.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 10/2/2018.
 */
public class Campaign {


    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("business_id")
    @Expose
    public String businessId;
    @SerializedName("image_cover")
    @Expose
    public String imageCover;
    @SerializedName("end_day")
    @Expose
    public String endDay;
    @SerializedName("title_en")
    @Expose
    public String titleEn;
    @SerializedName("title_ar")
    @Expose
    public String titleAr;
    @SerializedName("descrption_en")
    @Expose
    public String descrptionEn;
    @SerializedName("descrption_ar")
    @Expose
    public String descrptionAr;
    @SerializedName("number_images")
    @Expose
    public String numberImages;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public Object updatedAt;
    @SerializedName("deleted_at")
    @Expose
    public Object deletedAt;


}
