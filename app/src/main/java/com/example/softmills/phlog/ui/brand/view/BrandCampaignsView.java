package com.example.softmills.phlog.ui.brand.view;

import com.example.softmills.phlog.base.commonmodel.Campaign;

import java.util.List;

/**
 * Created by abdalla_maged On Dec,2018
 */
public interface BrandCampaignsView {
    void showMessage(String msg);
    void viewAllCampaign(List<Campaign> homeCampaignList);
    void showAllCampaignProgress(boolean state);
}
