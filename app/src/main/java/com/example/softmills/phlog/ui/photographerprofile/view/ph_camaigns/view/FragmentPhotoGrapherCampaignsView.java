package com.example.softmills.phlog.ui.photographerprofile.view.ph_camaigns.view;

import com.example.softmills.phlog.base.commonmodel.Campaign;

import java.util.List;

/**
 * Created by abdalla_maged on 9/30/2018.
 */
public interface FragmentPhotoGrapherCampaignsView {
    void showCampaigns(List<Campaign> campaignList);
    void showMessage(String msg);
    void setNextPageUrl(String page);
    void viewPhotoGrapherCampaignLoading(boolean state);

}
