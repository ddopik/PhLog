package com.example.softmills.phlog.ui.campaigns;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.ui.campaigns.model.Campaign;
import com.example.softmills.phlog.ui.campaigns.presenter.CampaignPresenter;
import com.example.softmills.phlog.ui.campaigns.presenter.CampaignPresenterImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 10/1/2018.
 */
public class CampaignsFragment extends BaseFragment implements CampaignFragmentView {


    private View mainView;
    private ProgressBar progressBar;
    CustomRecyclerView allCampaignsRv;
    private AllCampaignsAdapter allCampaignsAdapter;
    private List<Campaign> campaignList = new ArrayList<>();
    private CampaignPresenter campaignPresenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_campaigns, container, false);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initViews();

        campaignPresenter.getAllCampaign();
    }


    @Override
    protected void initViews() {
        progressBar = mainView.findViewById(R.id.all_home_campaign_progress_bar);
        allCampaignsRv = mainView.findViewById(R.id.all_campaigns_rv);
        allCampaignsAdapter = new AllCampaignsAdapter(getContext(), campaignList);
        allCampaignsRv.setAdapter(allCampaignsAdapter);

    }

    @Override
    protected void initPresenter() {
        campaignPresenter = new CampaignPresenterImpl(getContext(), this);
    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    @Override
    public void viewAllCampaign(List<Campaign> campaignList) {
        allCampaignsRv.setVisibility(View.VISIBLE);
        this.campaignList.clear();
        this.campaignList.addAll(campaignList);
        allCampaignsAdapter.notifyDataSetChanged();

    }

    @Override
    public void showAllCampaginProgress(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }

    }
}
