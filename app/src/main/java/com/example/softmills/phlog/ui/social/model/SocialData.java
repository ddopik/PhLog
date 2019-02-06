package com.example.softmills.phlog.ui.social.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 11/13/2018.
 */
public class SocialData {

    @SerializedName("sources")
    @Expose
    public List<Source> sources = new ArrayList<>();


}
