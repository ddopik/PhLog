package com.example.softmills.phlog.ui.commentimage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageCommentsResponse {

    @SerializedName("data")
    @Expose
    public ImageCommentsData data;
}
