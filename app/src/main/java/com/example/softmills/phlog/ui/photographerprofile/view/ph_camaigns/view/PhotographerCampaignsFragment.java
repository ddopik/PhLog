package com.example.softmills.phlog.ui.photographerprofile.view.ph_camaigns.view;
/**
 * Created by Abdalla_maged on 9/30/2018.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.commonmodel.Campaign;
import com.example.softmills.phlog.base.widgets.PagingController;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.ui.campaigns.inner.ui.CampaignInnerActivity;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_camaigns.presenter.FragmentPhotoGrapherCampaignsPresenter;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_camaigns.presenter.FragmentPhotoGrapherCampaignsPresenterImpl;


import java.util.ArrayList;
import java.util.List;


public class PhotographerCampaignsFragment extends BaseFragment implements FragmentPhotoGrapherCampaignsView {

    private View mainView;
    private List<Campaign> campaignList = new ArrayList<Campaign>();
    private photographerCampaignsAdapter photoGrapherCampaignsAdapter;
    private FragmentPhotoGrapherCampaignsPresenter photoGrapherCampaignsPresenter;
    private LinearLayoutManager mLayoutManager;
    private CustomRecyclerView campaignsRv;
    private PagingController pagingController;

    public static PhotographerCampaignsFragment getInstance() {

        return new PhotographerCampaignsFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_photographer_campains, container, false);
        mLayoutManager = new LinearLayoutManager(getContext());
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initViews();
        initListener();
        photoGrapherCampaignsPresenter.getPhotographerCampaigns(0);
    }

    @Override
    protected void initPresenter() {
        photoGrapherCampaignsPresenter = new FragmentPhotoGrapherCampaignsPresenterImpl(getContext(),this);
    }

    @Override
    protected void initViews() {
        photoGrapherCampaignsAdapter = new photographerCampaignsAdapter(getContext(), campaignList);
        campaignsRv = mainView.findViewById(R.id.campaigns_rv);
        campaignsRv.setAdapter(photoGrapherCampaignsAdapter);


    }

    private void initListener() {
        pagingController = new PagingController(campaignsRv) {
            @Override
            public void getPagingControllerCallBack(int page) {
                photoGrapherCampaignsPresenter.getPhotographerCampaigns(page + 1);
            }
        };

        photoGrapherCampaignsAdapter.campaignLister = campaignID -> {
            Intent intent = new Intent(getContext(), CampaignInnerActivity.class);
            intent.putExtra(CampaignInnerActivity.CAMPAIGN_ID, campaignID);
            startActivity(intent);
        };
    }


    @Override
    public void showCampaigns(List<Campaign> campaignList) {
        this.campaignList.addAll(campaignList);
        photoGrapherCampaignsAdapter.notifyDataSetChanged();
    }
}