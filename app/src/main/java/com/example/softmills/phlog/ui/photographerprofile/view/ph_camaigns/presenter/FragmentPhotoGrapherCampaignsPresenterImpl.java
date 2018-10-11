package com.example.softmills.phlog.ui.photographerprofile.view.ph_camaigns.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_camaigns.view.FragmentPhotoGrapherCampaignsView;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged on 9/30/2018.
 */
public class FragmentPhotoGrapherCampaignsPresenterImpl implements FragmentPhotoGrapherCampaignsPresenter {

    private Context context;
    private FragmentPhotoGrapherCampaignsView fragmentPhotoGrapherCampaignsView;


    public FragmentPhotoGrapherCampaignsPresenterImpl (Context context,FragmentPhotoGrapherCampaignsView fragmentPhotoGrapherCampaignsView){
        this.context=context;
        this.fragmentPhotoGrapherCampaignsView=fragmentPhotoGrapherCampaignsView;

    }


    public FragmentPhotoGrapherCampaignsPresenterImpl(FragmentPhotoGrapherCampaignsView fragmentPhotoGrapherCampaignsView){
        this.fragmentPhotoGrapherCampaignsView=fragmentPhotoGrapherCampaignsView;
    }


    @SuppressLint("CheckResult")
    @Override
    public void getPhotographerCampaigns(int pageNum) {
        BaseNetworkApi.getPhotoGrapherProfileCampaign(PrefUtils.getUserToken(context),pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(campaignResponse -> {
                    fragmentPhotoGrapherCampaignsView.showCampaigns(campaignResponse.data.data);
                },throwable -> {

                });


    }



}
