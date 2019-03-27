package com.example.softmills.phlog.ui.login.model;

import com.example.softmills.phlog.base.BaseApiResponse;
import com.example.softmills.phlog.base.commonmodel.BaseStateResponse;
import com.example.softmills.phlog.base.commonmodel.Photographer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SocialLoginResponse {


    @SerializedName("data")
    @Expose
    public Photographer data = null;


}
