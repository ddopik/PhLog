package com.example.softmills.phlog.ui.allphotos.model;

import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abdalla_maged On Dec,2018
 */
public class PhotoGrapherPhotosData {

    @SerializedName("current_page")
    @Expose
    public Integer currentPage;
    @SerializedName("data")
    @Expose
    public List<BaseImage> data = null;
    @SerializedName("first_page_url")
    @Expose
    public String firstPageUrl;
    @SerializedName("from")
    @Expose
    public Integer from;
    @SerializedName("last_page")
    @Expose
    public Integer lastPage;
    @SerializedName("last_page_url")
    @Expose
    public String lastPageUrl;
    @SerializedName("next_page_url")
    @Expose
    public String nextPageUrl;
    @SerializedName("path")
    @Expose
    public String path;
    @SerializedName("per_page")
    @Expose
    public Integer perPage;
    @SerializedName("prev_page_url")
    @Expose
    public String prevPageUrl;
    @SerializedName("to")
    @Expose
    public Integer to;
    @SerializedName("total")
    @Expose
    public Integer total;

}
