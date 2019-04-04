package com.example.softmills.phlog.ui.commentimage.model;

import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageDetailsResponse {


    @SerializedName("data")
    @Expose
    public BaseImage data;


}
