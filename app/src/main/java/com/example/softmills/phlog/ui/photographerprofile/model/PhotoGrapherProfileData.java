package com.example.softmills.phlog.ui.photographerprofile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 10/1/2018.
 */
public class PhotoGrapherProfileData {
    @SerializedName("follower_count")
    @Expose
    public String followerCount;
    @SerializedName("following_count")
    @Expose
    public String followingCount;
    @SerializedName("image_photographer_count")
    @Expose
    public String imagePhotographerCount;
    @SerializedName("image_profile")
    @Expose
    public String imageProfile;
    @SerializedName("full_name")
    @Expose
    public String fullName;

    @SerializedName("user_name")
    @Expose
    public String userName;
    @SerializedName("rate")
    @Expose
    public Integer rate;
}
