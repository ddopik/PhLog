package com.example.softmills.phlog.ui.login.model;

import com.example.softmills.phlog.base.BaseApiResponse;
import com.example.softmills.phlog.base.commonmodel.BaseStateResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SocialLoginResponse {


    @SerializedName("token")
    @Expose
    public String token = null;

    @SerializedName("user_name")
    @Expose
    public String userName;

    @SerializedName("state")
    public String state;
}
