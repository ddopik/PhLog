package com.example.softmills.phlog.ui.search.view.brand.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 10/31/2018.
 */
public class BrandSearch {
    @SerializedName("thumbnail")
    @Expose
    public String thumbnail;
    @SerializedName("cover_image")
    @Expose
    public String coverImage;
    @SerializedName("name_en")
    @Expose
    public String nameEn;
    @SerializedName("number_of_followers")
    @Expose
    public Integer numberOfFollowers;
    @SerializedName("id")
    @Expose
    public Integer id;
}
