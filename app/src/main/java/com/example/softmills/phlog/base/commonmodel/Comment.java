package com.example.softmills.phlog.base.commonmodel;

import com.example.softmills.phlog.base.commonmodel.Photographer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged On Dec,2018
 */
public class Comment {
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("comment")
    @Expose
    public String comment;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("photographer")
    @Expose
    public Photographer photographer;
}
