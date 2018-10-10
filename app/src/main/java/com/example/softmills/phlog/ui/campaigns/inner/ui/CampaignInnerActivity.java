package com.example.softmills.phlog.ui.campaigns.inner.ui;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.ui.campaigns.inner.presenter.CampaignInnerPresenter;
import com.example.softmills.phlog.ui.campaigns.inner.presenter.CampaignInnerPresenterImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by abdalla_maged on 10/4/2018.
 */
public class CampaignInnerActivity extends BaseActivity implements CampaignInnerActivityView {


    private final String TAG = CampaignInnerActivity.class.getSimpleName();
    private FrameLayout campaignImg;
    private TextView campaignTitle, campaignHostedBy, campaignDayLeft;

    private CampaignInnerPresenter campaignInnerPresenter;
    private OnMissionCampaignDataRecived onMissionCampaignDataRecived;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_innner);
        initPresenter();
        initView();

    }

    @Override
    public void initView() {

        campaignImg = findViewById(R.id.campaign_header_img);
        campaignTitle = findViewById(R.id.campaign_title);
        campaignHostedBy = findViewById(R.id.campaign_hosted_by);
        campaignDayLeft = findViewById(R.id.campaign_day_left);
        InnerCampaignFragmentPagerAdapter innerCampaignFragmentPagerAdapter = new InnerCampaignFragmentPagerAdapter(getSupportFragmentManager(), getFragmentPagerFragment(), getFragmentTitles());
        TabLayout campaignTabs = findViewById(R.id.inner_campaign_tabs);
        ViewPager campaignViewPager = findViewById(R.id.inner_campaign_viewpager);
        campaignViewPager.setAdapter(innerCampaignFragmentPagerAdapter);
        campaignTabs.setupWithViewPager(campaignViewPager);

        String campaignID = getIntent().getStringExtra("campaign_id");

        if (!campaignID.isEmpty())
            campaignInnerPresenter.getCampaignDetails("1");


    }

    @Override
    public void initPresenter() {
        campaignInnerPresenter = new CampaignInnerPresenterImpl(getBaseContext(), this);
    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }


    private List<Fragment> getFragmentPagerFragment() {
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        CampaignInnerMissionFragment campaignInnerMissionFragment = CampaignInnerMissionFragment.getInstance();
        onMissionCampaignDataRecived = campaignInnerMissionFragment;
        fragmentList.add(campaignInnerMissionFragment);
        fragmentList.add(CampaignInnerPhotosFragment.getInstance("1"));
        return fragmentList;
    }

    private List<String> getFragmentTitles() {
        List<String> fragmentList = new ArrayList<String>();
        fragmentList.add(getResources().getString(R.string.tab_mission));
        fragmentList.add(getResources().getString(R.string.tab_photos));
        return fragmentList;
    }

    @Override
    public void viewCampaignHeaderImg(String img) {
        GlideApp.with(this).load(img)
                .error(R.drawable.splash_screen_background)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, Transition<? super Drawable> transition) {
                        campaignImg.setBackground(resource);
                    }
                });

//        GlideApp.with(this).load(img).error(R.drawable.splash_screen_background).into(campaignImg);


    }

    @SuppressLint("SetTextI18n")
    @Override
    public void viewCampaignTitle(String name) {
        campaignTitle.setText(name);
    }

    @Override
    public void viewCampaignLeftDays(String dayLeft) {
        campaignDayLeft.setText(dayLeft);
        campaignDayLeft.append("  ");
        campaignDayLeft.append(getResources().getString(R.string.days_left));
    }

    @Override
    public void viewCampaignHostedBy(String hostName) {
        campaignHostedBy.setText(getResources().getString(R.string.hosted_by));
        campaignHostedBy.append(hostName);

    }

    @Override
    public void viewCampaignMissionDescription(String missionDesc) {
        Log.e(TAG, "viewCampaignMissionDescription () --->" + missionDesc);
        if (onMissionCampaignDataRecived != null && missionDesc != null)
            onMissionCampaignDataRecived.onCampaignDescription(missionDesc);
    }

    public interface OnMissionCampaignDataRecived {
        void onCampaignDescription(String desc);
    }
}
