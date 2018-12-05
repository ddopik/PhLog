package com.example.softmills.phlog.ui.login.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginData {
    @SerializedName("is_phone_verified")
    @Expose
    public Boolean isPhoneVerified;
    @SerializedName("user_name")
    @Expose
    public String userName;
    @SerializedName("is_email_verified")
    @Expose
    public Boolean isEmailVerified;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("mobile_model")
    @Expose
    public String mobileModel;
    @SerializedName("facebook_id")
    @Expose
    public String facebookId;
    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("full_name")
    @Expose
    public String fullName;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("mobile_os")
    @Expose
    public String mobileOs;
    @SerializedName("hash")
    @Expose
    public String hash;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("country_id")
    @Expose
    public String countryId;
    @SerializedName("id")
    @Expose
    public String id;

}
