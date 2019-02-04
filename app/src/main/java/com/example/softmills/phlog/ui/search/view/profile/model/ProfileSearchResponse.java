package com.example.softmills.phlog.ui.search.view.profile.model;

import com.example.softmills.phlog.base.commonmodel.Photographer;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.following.model.PhotoGrapherFollowingInData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abdalla_maged on 11/1/2018.
 */
public class ProfileSearchResponse {


    @SerializedName("data")
    @Expose
    public ProfileSearchData data = null;

}
