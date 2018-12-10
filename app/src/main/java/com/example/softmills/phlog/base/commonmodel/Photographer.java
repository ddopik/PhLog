package com.example.softmills.phlog.base.commonmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class Photographer {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("user_name")
    @Expose
    public String userName;
    @SerializedName("full_name")
    @Expose
    public String fullName;
}
