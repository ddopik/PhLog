package com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.brand.view;

import com.example.softmills.phlog.base.commonmodel.Business;

import java.util.List;

/**
 * Created by abdalla_maged on 11/11/2018.
 */
public interface PhotoGrapherBrandFragmentView {

    void viewPhotoGrapherFollowingBrand(List<Business> brandSearchList);
    void viewPhotoGrapherFollowingBrandProgress(boolean state);
    void setNextPageUrl(String page);

    void showMessage(String msg);
}
