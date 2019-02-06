package com.example.softmills.phlog.ui.commentimage.model;

import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageRateResponse   {
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("data")
    @Expose
    public BaseImage baseImage;

}
