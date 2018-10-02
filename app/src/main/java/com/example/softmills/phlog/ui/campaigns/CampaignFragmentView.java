package com.example.softmills.phlog.ui.campaigns;

import com.example.softmills.phlog.ui.campaigns.model.Campaign;

import java.util.List;

/**
 * Created by abdalla_maged on 10/2/2018.
 */
public interface CampaignFragmentView {

    void viewAllCampaign(List<Campaign> campaignList);

    void showAllCampaginProgress(boolean state);

}
