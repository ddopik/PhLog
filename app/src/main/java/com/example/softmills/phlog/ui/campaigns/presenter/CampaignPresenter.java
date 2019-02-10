package com.example.softmills.phlog.ui.campaigns.presenter;

import io.reactivex.Observable;

/**
 * Created by abdalla_maged on 10/2/2018.
 */
public interface CampaignPresenter {
    void getAllCampaign(int page);
    Observable<Boolean> joinCampaign(String campaignID);
}
