package com.example.softmills.phlog.ui.social.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abdalla_maged on 11/13/2018.
 */
public class Entite {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("display_type")
    @Expose
    public String displayType;
    @SerializedName("isIn")
    @Expose
    public Boolean isIn;
    @SerializedName("imgs")
    @Expose
    public List<String> imgs = null;
    @SerializedName("full_name")
    @Expose
    public String fullName;
    @SerializedName("user_name")
    @Expose
    public String userName;
    @SerializedName("gotoListImgs_Or_singleImg_screen")
    @Expose
    public Boolean gotoListImgsOrSingleImgScreen;
    @SerializedName("title_en")
    @Expose
    public String titleEn;
    @SerializedName("name_en")
    @Expose
    public String nameEn;
    @SerializedName("number_of_followers")
    @Expose
    public String numberOfFollowers;
    @SerializedName("description_en")
    @Expose
    public String descriptionEn;
    @SerializedName("industry_name")
    @Expose
    public String industryName;
    @SerializedName("thumbnail")
    @Expose
    public String thumbnail;
    @SerializedName("brandCoverimg")
    @Expose
    public String brandCoverimg;
}
