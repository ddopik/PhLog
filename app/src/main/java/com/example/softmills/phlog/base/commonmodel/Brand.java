package com.example.softmills.phlog.base.commonmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged On Dec,2018
 */
public class Brand {
    @SerializedName("image_cover")
    @Expose
    public String imageCover;
    @SerializedName("thumbnail")
    @Expose
    public String thumbnail;
    @SerializedName("full_name")
    @Expose
    public String fullName;
    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("user_name")
    @Expose
    public String userName;
    @SerializedName("is_brand")
    @Expose
    public Integer isBrand;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("followers_count")
    @Expose
    public Integer followersCount;
    @SerializedName("is_follow")
    @Expose
    public Boolean isFollow;
    @SerializedName("is_brand_text")
    @Expose
    public String isBrandText;
    @SerializedName("website")
    @Expose
    public String website;
    @SerializedName("description")
    @Expose
    public String description;
}
