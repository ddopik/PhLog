package com.example.softmills.phlog.ui.search.view.album.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abdalla_maged on 11/6/2018.
 */
public class AlbumSearch {
    @SerializedName("covers")
    @Expose
    public List<String> covers = null;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("number_of_photos")
    @Expose
    public Integer numberOfPhotos;
    @SerializedName("id")
    @Expose
    public Integer id;
}
