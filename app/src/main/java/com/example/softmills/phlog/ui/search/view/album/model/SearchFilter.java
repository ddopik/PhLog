package com.example.softmills.phlog.ui.search.view.album.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abdalla_maged on 10/30/2018.
 */
public class SearchFilter {
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("options")
    @Expose
    public List<String> options = null;
}
