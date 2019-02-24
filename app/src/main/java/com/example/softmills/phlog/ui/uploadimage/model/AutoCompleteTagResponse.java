package com.example.softmills.phlog.ui.uploadimage.model;

import com.example.softmills.phlog.base.commonmodel.Tag;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AutoCompleteTagResponse {

    @SerializedName("data")
    @Expose
    public List<Tag> data = null;
}
