package com.example.softmills.phlog.ui.commentimage.model;

 import com.example.softmills.phlog.base.commonmodel.Mentions;
 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageCommentsData {

    @SerializedName("comments")
    @Expose
    public ImageCommentsObj comments;
    @SerializedName("mentions")
    @Expose
    public Mentions mentions;
    @SerializedName("first_page_url")
    @Expose
    public String firstPageUrl;
    @SerializedName("from")
    @Expose
    public String from;
    @SerializedName("last_page")
    @Expose
    public String lastPage;
    @SerializedName("last_page_url")
    @Expose
    public String lastPageUrl;
    @SerializedName("next_page_url")
    @Expose
    public String nextPageUrl;
}
