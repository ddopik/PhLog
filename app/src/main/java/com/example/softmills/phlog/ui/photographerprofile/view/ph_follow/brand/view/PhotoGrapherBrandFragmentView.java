package com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.brand.view;

import com.example.softmills.phlog.base.commonmodel.Brand;

import java.util.List;

/**
 * Created by abdalla_maged on 11/11/2018.
 */
public interface PhotoGrapherBrandFragmentView {

    void viewPhotoGrapherFollowingBrand(List<Brand> brandSearchList);
    void viewPhotoGrapherFollowingBrandProgress(boolean state);
    void showMessage(String msg);
}
