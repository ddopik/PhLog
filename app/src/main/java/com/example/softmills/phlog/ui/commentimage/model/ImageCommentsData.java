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
}
