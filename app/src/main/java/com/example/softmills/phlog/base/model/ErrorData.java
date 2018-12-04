package com.example.softmills.phlog.base.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abdalla_maged On Dec,2018
 */
public class ErrorData {


    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("code")
    @Expose
    public Integer code;
}
