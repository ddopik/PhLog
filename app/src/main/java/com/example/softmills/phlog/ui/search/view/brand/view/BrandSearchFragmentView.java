package com.example.softmills.phlog.ui.search.view.brand.view;

import com.example.softmills.phlog.base.commonmodel.Business;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.brand.model.PhotographerFollowingBrandData;
import com.example.softmills.phlog.ui.search.view.brand.model.BrandSearchData;

import java.util.List;

/**
 * Created by abdalla_maged on 10/31/2018.
 */
public interface BrandSearchFragmentView {

    void viewBrandSearchItems(BrandSearchData brandSearchData);
    void viewBrandSearchProgress(boolean state);
    void showMessage(String msg);
}
