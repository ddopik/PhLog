package com.example.softmills.phlog.ui.userprofile.view.campaigns.presenter;

import com.example.softmills.phlog.ui.userprofile.view.campaigns.model.Campaign;
import com.example.softmills.phlog.ui.userprofile.view.campaigns.view.UserCampaignsFragment;
import com.example.softmills.phlog.ui.userprofile.view.campaigns.view.UserCampaignsFragmentView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by $USER_NAME on 9/27/2018.
 */
public class UserCampaignsPresenterImp implements UserCampaignsPresenter {
    UserCampaignsFragmentView userCampaignsFragmentView;

    public  UserCampaignsPresenterImp(UserCampaignsFragmentView userCampaignsFragmentView){
        this.userCampaignsFragmentView=userCampaignsFragmentView;
    }
    @Override
    public void getUserCampaigns() {
        List<Campaign> campaignList=new ArrayList<>();
        Campaign campaign=new Campaign();
        campaign.campaignUrl="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQWaOmIS9ixNdSeIOvp2ie9lx_IuPF2dKmwTRMzadoZhcYVCrol";
        campaignList.add(campaign);
        campaignList.add(campaign);
        campaignList.add(campaign);
        campaignList.add(campaign);
        campaignList.add(campaign);
        campaignList.add(campaign);
        campaignList.add(campaign);
        campaignList.add(campaign);
        campaignList.add(campaign);
        campaignList.add(campaign);
        campaignList.add(campaign);
        campaignList.add(campaign);
        campaignList.add(campaign);

        userCampaignsFragmentView.showCampaigns(campaignList);
    }
}
