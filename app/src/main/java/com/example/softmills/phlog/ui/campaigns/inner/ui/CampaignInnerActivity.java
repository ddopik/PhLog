package com.example.softmills.phlog.ui.campaigns.inner.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.Constants;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.base.commonmodel.UploadImageType;
import com.example.softmills.phlog.ui.allphotos.view.AllPhotographerPhotosActivity;
import com.example.softmills.phlog.ui.campaigns.inner.presenter.CampaignInnerPresenter;
import com.example.softmills.phlog.ui.campaigns.inner.presenter.CampaignInnerPresenterImpl;
import com.example.softmills.phlog.ui.uploadimage.view.UploadImageActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 10/4/2018.
 */
public class CampaignInnerActivity extends BaseActivity implements CampaignInnerActivityView {


    private final String TAG = CampaignInnerActivity.class.getSimpleName();
    public static String CAMPAIGN_ID = "campaign_id";
    private String campaignId;

    private FrameLayout campaignImg;
    private Button uploadCampaignBtn;
    private TextView campaignTitle, campaignHostedBy, campaignDayLeft;
    private TabLayout campaignTabs;
    private ViewPager campaignViewPager;
    private CampaignInnerPresenter campaignInnerPresenter;
    private OnMissionCampaignDataRecived onMissionCampaignDataRecived;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_innner);
        initPresenter();
        initView();
        initListener();

    }

    @Override
    public void initView() {

        campaignImg = findViewById(R.id.campaign_header_img);
        campaignTitle = findViewById(R.id.campaign_title);
        campaignHostedBy = findViewById(R.id.campaign_hosted_by);
        campaignDayLeft = findViewById(R.id.campaign_header_day_left);
        uploadCampaignBtn = findViewById(R.id.upload_campaign_photo_id);
        campaignTabs = findViewById(R.id.inner_campaign_tabs);
        campaignViewPager = findViewById(R.id.inner_campaign_viewpager);
        if (getIntent().getStringExtra(CAMPAIGN_ID) != null) {
            campaignInnerPresenter.getCampaignDetails(getIntent().getStringExtra(CAMPAIGN_ID));
            campaignId = getIntent().getStringExtra(CAMPAIGN_ID);
        }

    }

    @Override
    public void initPresenter() {
        campaignInnerPresenter = new CampaignInnerPresenterImpl(getBaseContext(), this);
    }

    private void initListener() {
        uploadCampaignBtn.setOnClickListener(v -> {


            showPhotoDialog(this, "title", "Message");


        });
    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
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
    public void viewCampaignMissionDescription(String missionDesc, int photosCount) {

        InnerCampaignFragmentPagerAdapter innerCampaignFragmentPagerAdapter = new InnerCampaignFragmentPagerAdapter(getSupportFragmentManager(), getFragmentPagerFragment(), getFragmentTitles(photosCount));
        if (onMissionCampaignDataRecived != null && missionDesc != null) {
            campaignViewPager.setAdapter(innerCampaignFragmentPagerAdapter);
            campaignTabs.setupWithViewPager(campaignViewPager);
            onMissionCampaignDataRecived.onCampaignDescription(missionDesc);

        }
    }

    private List<Fragment> getFragmentPagerFragment() {
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        CampaignInnerMissionFragment campaignInnerMissionFragment = CampaignInnerMissionFragment.getInstance();
        onMissionCampaignDataRecived = campaignInnerMissionFragment; // pass mission description to campaignInnerMissionFragment
        fragmentList.add(campaignInnerMissionFragment);
        fragmentList.add(CampaignInnerPhotosFragment.getInstance(getIntent().getStringExtra(CAMPAIGN_ID)));
        return fragmentList;
    }

    private List<String> getFragmentTitles(int photosCount) {
        List<String> fragmentList = new ArrayList<String>();
        fragmentList.add(getResources().getString(R.string.tab_mission));
        fragmentList.add(photosCount + " " + getResources().getString(R.string.tab_photos));
        return fragmentList;
    }

    public void showPhotoDialog(Activity activity, String title, CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        if (title != null) builder.setTitle(title);

        builder.setMessage(message);
        builder.setPositiveButton(R.string.phone, (dialog, id) -> {

            UploadImageType uploadImageType = new UploadImageType();
            uploadImageType.setUploadImageType(Constants.UploadImageTypes.CAMPAIGN_IMG);
            uploadImageType.setImageId(campaignId);

//            HashMap<String, String> imageType=new HashMap<String, String>();
//            extras.putSerializable(UploadImageActivity.IMAGE_TYPE,imageType);
//            imageType.put(IMAGE_TYPE_CAMPAIGN,campaignId);
//
            Bundle extras = new Bundle();
            extras.putSerializable(UploadImageActivity.IMAGE_TYPE, uploadImageType);

            Intent intent = new Intent(this, UploadImageActivity.class);
            intent.putExtras(extras);
            startActivity(intent);
        });
        builder.setNegativeButton(R.string.photos, (dialog, id) -> {
            Intent intent = new Intent(this, AllPhotographerPhotosActivity.class);
            startActivity(intent);
        });
        builder.show();
    }

    public interface OnMissionCampaignDataRecived {
        void onCampaignDescription(String desc);
    }
}
