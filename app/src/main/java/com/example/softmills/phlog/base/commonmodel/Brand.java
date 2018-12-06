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
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("full_name")
    @Expose
    public String fullName;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("user_name")
    @Expose
    public String userName;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("is_brand")
    @Expose
    public Integer isBrand;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("num_of_followers")
    @Expose
    public Integer numOfFollowers;
    @SerializedName("is_brand_text")
    @Expose
    public String isBrandText;
}
