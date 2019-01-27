package com.example.softmills.phlog.ui.search.view.album.model;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 10/30/2018.
 */
public class SearchFilter {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("system_name")
    @Expose
    public String systemName;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    public Object deletedAt;
    @SerializedName("display_name")
    @Expose
    public String displayName;
    @SerializedName("filters")
    @Expose
    public List<FilterOption> options = null;

}
