package com.example.softmills.phlog.ui.login.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SocialLoginResponse {

    @SerializedName("data")
    @Expose
    public SocialFbData data;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("found")
    @Expose
    public String found;
}
