package com.example.softmills.phlog.ui.brand.presenter;

import io.reactivex.Observable;

/**
 * Created by abdalla_maged On Dec,2018
 */
public interface BrandCampaignsPresenter {
    void getBrandCampaigns(String brandId,String page);
    Observable<Boolean> joinCampaign(String campaignId);
}
