package com.example.softmills.phlog.ui.login.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {


    @SerializedName("data")
    @Expose
    public LoginData loginData;
}
