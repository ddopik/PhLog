package com.example.softmills.phlog.ui.campaigns.inner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseActivity;

import java.util.List;

/**
 * Created by abdalla_maged on 10/4/2018.
 */
public class CampaignInnerActivity extends BaseActivity {

    private TabLayout campaignTabs;
    private ViewPager campaignViewPager;
    private InnerCampaignFragmentPagerAdapter innerCampaignFragmentPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_innner);
        initView();
        initPresenter();
    }

    @Override
    public void initView() {

        innerCampaignFragmentPagerAdapter=new InnerCampaignFragmentPagerAdapter(getFragmentPagerFragment(),getFragmentTitles());

        campaignTabs = findViewById(R.id.inner_campaign_tabs);
        campaignViewPager = findViewById(R.id.inner_campaign_viewpager);
        campaignTabs.setupWithViewPager(campaignViewPager);
        campaignViewPager.setAdapter(innerCampaignFragmentPagerAdapter);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }


    private List<Fragment> getFragmentPagerFragment()

    {

    }

    private List<String> getFragmentTitles() {

    }
}
