package com.example.softmills.phlog.ui.search.view.brand.view;

import com.example.softmills.phlog.base.commonmodel.Business;

import java.util.List;

/**
 * Created by abdalla_maged on 10/31/2018.
 */
public interface BrandSearchFragmentView {

    void viewBrandSearchItems(List<Business> brandSearchList);
    void viewBrandSearchProgress(boolean state);
    void showMessage(String msg);
}
