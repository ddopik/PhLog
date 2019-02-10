package com.example.softmills.phlog.ui.brand.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.base.commonmodel.Campaign;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.base.widgets.PagingController;
import com.example.softmills.phlog.ui.brand.presenter.BrandCampaignsPresenter;
import com.example.softmills.phlog.ui.brand.presenter.BrandCampaignsPresenterImpl;
import com.example.softmills.phlog.ui.campaigns.AllCampaignsAdapter;
import com.example.softmills.phlog.ui.campaigns.inner.ui.CampaignInnerActivity;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

/**
 * Created by abdalla_maged On Dec,2018
 */
public class BrandCampaignsActivity extends BaseActivity implements BrandCampaignsView {


    public static String BRAND_ID="brand_id";
    private String brandId;
    private ProgressBar progressBar;
    private CustomRecyclerView allCampaignsRv;
    private AllCampaignsAdapter allCampaignsAdapter;
    private List<Campaign> homeCampaignList = new ArrayList<>();
    private BrandCampaignsPresenter campaignPresenter;
    private PagingController pagingController;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_campains);

        if (getIntent().getIntExtra(BRAND_ID,0) !=0) {
            brandId=String.valueOf(getIntent().getIntExtra(BRAND_ID,0));
            initPresenter();
            initView();
            initListener();
            campaignPresenter.getBrandCampaigns(brandId,"0");
        }
    }

    @Override

    public void initView() {
        progressBar = findViewById(R.id.all_home_campaign_progress_bar);
        allCampaignsRv = findViewById(R.id.all_campaigns_rv);
        allCampaignsAdapter = new AllCampaignsAdapter(this, homeCampaignList);
        allCampaignsRv.setAdapter(allCampaignsAdapter);
    }

    @Override
    public void initPresenter() {
        campaignPresenter = new BrandCampaignsPresenterImpl(this, this);
    }

    private void initListener() {

        pagingController = new PagingController(allCampaignsRv) {
            @Override
            public void getPagingControllerCallBack(int page) {
                campaignPresenter.getBrandCampaigns(brandId,String.valueOf(page));
            }
        };
        allCampaignsAdapter.campaignLister = new AllCampaignsAdapter.CampaignLister() {
            @Override
            public void onCampaignClicked(String campaignID) {
                Intent intent = new Intent(BrandCampaignsActivity.this, CampaignInnerActivity.class);
                intent.putExtra(CampaignInnerActivity.CAMPAIGN_ID, String.valueOf(campaignID));
                startActivity(intent);
            }

            @Override
            public void onFollowCampaign(Campaign campaign) {
                campaignPresenter.joinCampaign(campaign.id.toString());
            }
        };

    }


    @Override
    public void viewAllCampaign(List<Campaign> homeCampaignList) {
        allCampaignsRv.setVisibility(View.VISIBLE);
        this.homeCampaignList.addAll(homeCampaignList);
        allCampaignsAdapter.notifyDataSetChanged();

    }

    @Override
    public void showAllCampaignProgress(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }
}
