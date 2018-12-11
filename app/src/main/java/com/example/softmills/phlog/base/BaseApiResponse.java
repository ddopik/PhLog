package com.example.softmills.phlog.base;

import com.example.softmills.phlog.base.commonmodel.BaseErrorResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abdalla_maged on 10/10/2018.
 */
public class BaseApiResponse {



    @SerializedName("errors")
    @Expose
    public List<BaseErrorResponse> errors = null;

}
