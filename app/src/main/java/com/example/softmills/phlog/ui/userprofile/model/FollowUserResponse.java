package com.example.softmills.phlog.ui.userprofile.model;

import com.example.softmills.phlog.base.BaseApiResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 10/17/2018.
 */
public class FollowUserResponse extends BaseApiResponse {
    @SerializedName("data")
    @Expose
    public String data;
}
