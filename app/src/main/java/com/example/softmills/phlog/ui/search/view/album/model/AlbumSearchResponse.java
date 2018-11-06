package com.example.softmills.phlog.ui.search.view.album.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 11/6/2018.
 */
public class AlbumSearchResponse {

    @SerializedName("data")
    @Expose
    public AlbumSearchData data;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("state")
    @Expose
    public String state;
}
