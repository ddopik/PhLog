package com.example.softmills.phlog.ui.userprofile.view.campaigns.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.ui.userprofile.view.campaigns.model.Campaign;
import com.example.softmills.phlog.ui.userprofile.view.campaigns.presenter.UserCampaignsPresenter;
import com.example.softmills.phlog.ui.userprofile.view.campaigns.presenter.UserCampaignsPresenterImp;

import java.util.ArrayList;
import java.util.List;

public class UserCampaignsFragment extends BaseFragment implements UserCampaignsFragmentView {


    private View mainView;
    private List<Campaign> campaignList=new ArrayList<Campaign>();
    private UserCampaignsAdapter userCampaignsAdapter;
    private UserCampaignsPresenter userCampaignsPresenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_photographer_campains, container, false);
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initViews();
        initListener();

        userCampaignsPresenter.getUserCampaigns();
    }

    @Override
    protected void initPresenter() {
        userCampaignsPresenter =new UserCampaignsPresenterImp(this) ;

    }

    @Override
    protected void initViews() {
        userCampaignsAdapter = new UserCampaignsAdapter(getContext(), campaignList);
        CustomRecyclerView campaignsRv = mainView.findViewById(R.id.campaigns_rv);
        campaignsRv.setAdapter(userCampaignsAdapter);

    }

    private void initListener() {

    }

    @Override
    public void showCampaigns(List<Campaign> campaignList) {
        this.campaignList.clear();
        this.campaignList.addAll(campaignList);
        userCampaignsAdapter.notifyDataSetChanged();
    }
}
