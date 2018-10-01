package com.example.softmills.phlog.ui.photographerprofile.view.ph_camaigns.presenter;

import com.example.softmills.phlog.ui.photographerprofile.view.ph_camaigns.view.FragmentPhotoGrapherCampaignsView;
import com.example.softmills.phlog.ui.userprofile.view.campaigns.model.Campaign;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 9/30/2018.
 */
public class FragmentPhotoGrapherCampaignsPresenterImpl implements FragmentPhotoGrapherCampaignsPresenter {

    private FragmentPhotoGrapherCampaignsView fragmentPhotoGrapherCampaignsView;
    public FragmentPhotoGrapherCampaignsPresenterImpl(FragmentPhotoGrapherCampaignsView fragmentPhotoGrapherCampaignsView){
        this.fragmentPhotoGrapherCampaignsView=fragmentPhotoGrapherCampaignsView;
    }


    @Override
    public void getPhotographerCampaigns(int pageNum) {
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


        fragmentPhotoGrapherCampaignsView.showCampaigns(campaignList);
    }

    @Override
    public void getPhotographerSavedPhotos(int pageNum) {

    }

}
