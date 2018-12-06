package com.example.softmills.phlog.ui.photographerprofile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abdalla_maged on 10/1/2018.
 */
public class PhotoGrapherProfileData {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("follower_count")
    @Expose
    public int followerCount;
    @SerializedName("following_count")
    @Expose
    public Integer followingCount;
    @SerializedName("photo_count")
    @Expose
    public int photoCount;
    @SerializedName("image_profile")
    @Expose
    public String imageProfile;
    @SerializedName("user_name")
    @Expose
    public String userName;
    @SerializedName("full_name")
    @Expose
    public String fullName;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("rate")
    @Expose
    public int rate;
    @SerializedName("level")
    @Expose
    public String level;
    @SerializedName("earnings")
    @Expose
    public List <PhotoGrapherEarning> earnings;
}
