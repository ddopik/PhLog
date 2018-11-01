package com.example.softmills.phlog.ui.search.view.brand.view;

import com.example.softmills.phlog.ui.search.view.brand.model.BrandSearch;

import java.util.List;

/**
 * Created by abdalla_maged on 10/31/2018.
 */
public interface BrandSearchFragmentView {

    void viewBrandSearchItems(List<BrandSearch> brandSearchList);
    void viewBrandSearchProgress(boolean state);
    void showMessage(String msg);
}
