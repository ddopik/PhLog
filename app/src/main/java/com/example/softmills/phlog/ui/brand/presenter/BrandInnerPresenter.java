package com.example.softmills.phlog.ui.brand.presenter;

/**
 * Created by abdalla_maged on 11/12/2018.
 */
public interface BrandInnerPresenter {

    void getBrandInnerData(int brandID);
    void followBrand(String brandID);
     void unFollowBrand(String id);
}
