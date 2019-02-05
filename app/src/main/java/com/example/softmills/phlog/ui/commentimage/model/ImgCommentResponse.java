package com.example.softmills.phlog.ui.commentimage.model;

import com.example.softmills.phlog.ui.album.model.ImageCommentData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class ImgCommentResponse {
    @SerializedName("data")
    @Expose
    public ImageCommentData data;
}
