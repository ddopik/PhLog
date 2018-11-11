package com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.brand.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 11/11/2018.
 */
public class PhotographerFollowingBrand {
    @SerializedName("thumbnail")
    @Expose
    public String thumbnail;
    @SerializedName("cover_image")
    @Expose
    public String coverImage;
    @SerializedName("name_en")
    @Expose
    public String nameEn;
    @SerializedName("number_of_followers")
    @Expose
    public String numberOfFollowers;
    @SerializedName("id")
    @Expose
    public String id;
}
