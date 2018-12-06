package com.example.softmills.phlog.base.commonmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abdalla_maged On Dec,2018
 */
public class ErrorMessageResponse {
    @SerializedName("errors")
    @Expose
    public List<ErrorData> errors = null;

}
