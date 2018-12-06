package com.example.softmills.phlog.ui.brand.model;

import com.example.softmills.phlog.base.commonmodel.Brand;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 11/12/2018.
 */
public class BrandInnerResponse {
    @SerializedName("data")
    @Expose
    public Brand brand;


}
