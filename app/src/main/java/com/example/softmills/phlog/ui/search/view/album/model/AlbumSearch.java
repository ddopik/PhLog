package com.example.softmills.phlog.ui.search.view.album.model;

import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abdalla_maged on 11/6/2018.
 */
public class AlbumSearch {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("photos")
    @Expose
    public List<BaseImage> photos = null;
    @SerializedName("photos_count")
    @Expose
    public Integer photosCount;


}
