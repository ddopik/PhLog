package com.example.softmills.phlog.ui.search.view.brand.presenter;

import com.example.softmills.phlog.base.commonmodel.Business;

import io.reactivex.Observable;

/**
 * Created by abdalla_maged on 10/31/2018.
 */
public interface BrandSearchFragmentPresenter {

     void getSearchBrand(String key,int page);


     Observable<Boolean> unfollowBrand(Business business);

     Observable<Boolean> followBrand(Business business);
}
