package com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.brand.view;

import com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.brand.model.PhotographerFollowingBrand;
import com.example.softmills.phlog.ui.search.view.brand.model.BrandSearch;

import java.util.List;

/**
 * Created by abdalla_maged on 11/11/2018.
 */
public interface PhotoGrapherBrandFragmentView {

    void viewPhotoGrapherFollowingBrand(List<PhotographerFollowingBrand> brandSearchList);
    void viewPhotoGrapherFollowingBrandProgress(boolean state);
    void showMessage(String msg);
}
