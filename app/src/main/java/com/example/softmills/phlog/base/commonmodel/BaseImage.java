package com.example.softmills.phlog.base.commonmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class BaseImage {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("is_liked")
    @Expose
    public String isLiked;

    @SerializedName("url")
    @Expose
    public String image;
    @SerializedName("thumbnail_url")
    @Expose
    public String thumbnailUrl;


    @SerializedName("caption")
    @Expose
    public String caption;
    @SerializedName("is_for_sale")
    @Expose
    public String isForSale;

    @SerializedName("location")
    @Expose
    public String location;
    @SerializedName("can_sold_exclussive")
    @Expose
    public String canSoldExclussive;

    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose

    public String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    public String deletedAt;

    @SerializedName("photographer")
    @Expose
    public Photographer photographer;


    @SerializedName("photographer_id")
    @Expose
    public String photographerId;


    @SerializedName("image_cover")
    @Expose
    public String comment;

    @SerializedName("tags")
    @Expose
    public Tags tags;

}
