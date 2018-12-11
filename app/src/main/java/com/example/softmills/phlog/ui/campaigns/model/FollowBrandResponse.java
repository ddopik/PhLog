package com.example.softmills.phlog.ui.campaigns.model;

import com.example.softmills.phlog.base.commonmodel.BaseErrorResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abdalla_maged on 11/15/2018.
 */
public class FollowBrandResponse {
    @SerializedName("errors")
    @Expose
    public List<BaseErrorResponse> errors = null;
    @SerializedName("msg")
    @Expose
    public String msg;


}
