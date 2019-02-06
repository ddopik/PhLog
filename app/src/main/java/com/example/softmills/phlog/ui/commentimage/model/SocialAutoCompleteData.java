package com.example.softmills.phlog.ui.commentimage.model;


import com.example.softmills.phlog.base.commonmodel.Business;
import com.example.softmills.phlog.base.commonmodel.Photographer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SocialAutoCompleteData {
    @SerializedName("photographers")
    @Expose
    public List<Photographer> photographers;
    @SerializedName("businesses")
    @Expose
    public List<Business> businesses;
}
