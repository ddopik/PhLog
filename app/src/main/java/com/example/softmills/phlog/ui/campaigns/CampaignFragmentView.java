package com.example.softmills.phlog.ui.campaigns;

import com.example.softmills.phlog.base.commonmodel.Campaign;

import java.util.List;

/**
 * Created by abdalla_maged on 10/2/2018.
 */
public interface CampaignFragmentView {

    void viewAllCampaign(List<Campaign> homeCampaignList);

    void showAllCampaignProgress(boolean state);

    void showMessage(String msg);

}
