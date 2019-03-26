package com.example.softmills.phlog.ui.campaigns.inner.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.content.res.AppCompatResources;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.Constants;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.base.commonmodel.Campaign;
import com.example.softmills.phlog.base.commonmodel.UploadImageType;
import com.example.softmills.phlog.ui.allphotos.view.AllPhotographerPhotosActivity;
import com.example.softmills.phlog.ui.campaigns.inner.presenter.CampaignInnerPresenter;
import com.example.softmills.phlog.ui.campaigns.inner.presenter.CampaignInnerPresenterImpl;
import com.example.softmills.phlog.ui.dialog.dialog1.view.Dialog1Fragment;
import com.example.softmills.phlog.ui.uploadimage.view.UploadImageActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.softmills.phlog.Utiltes.Constants.CampaignStatus.CAMPAIGN_STATUS_APPROVED;
import static com.example.softmills.phlog.Utiltes.Constants.CampaignStatus.CAMPAIGN_STATUS_RUNNING;

/**
 * Created by abdalla_maged on 10/4/2018.
 */
public class
CampaignInnerActivity extends BaseActivity implements CampaignInnerActivityView {


    private final String TAG = CampaignInnerActivity.class.getSimpleName();
    public static String CAMPAIGN_ID = "campaign_id";
    private String campaignId;

    private ImageView campaignImg;
    private Button uploadCampaignBtn;
    private TextView campaignTitle, campaignHostedBy, campaignDayLeft;
    private TabLayout campaignTabs;
    private ViewPager campaignViewPager;
    private CampaignInnerPresenter campaignInnerPresenter;
//    private OnMissionCampaignDataRecived onMissionCampaignDataRecived;

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
//            showPhotoDialog(this, "title", "Message");

            new Dialog1Fragment.Builder(this)
                    .title(R.string.upload_photo_for_campaign)
                    .message(R.string.select_option)
                    .option0(R.string.select_from_photos)
                    .option1(R.string.upload_new_photo)
                    .cancelable(true)
                    .optionConsumer((dialog1Fragment, integer) -> {
                        switch (integer.intValue()) {
                            case 0:
                                Intent i2 = new Intent(this, AllPhotographerPhotosActivity.class);
                                i2.putExtra(AllPhotographerPhotosActivity.CAMPAIGN_ID, String.valueOf(campaignId));
                                startActivity(i2);
                                dialog1Fragment.dismiss();
                                break;
                            case 1:
                                UploadImageType uploadImageType = new UploadImageType();
                                uploadImageType.setUploadImageType(Constants.UploadImageTypes.CAMPAIGN_IMG);
                                uploadImageType.setImageId(campaignId);
                                Bundle extras = new Bundle();
                                extras.putSerializable(UploadImageActivity.IMAGE_TYPE, uploadImageType);
                                Intent i1 = new Intent(this, UploadImageActivity.class);
                                i1.putExtras(extras);
                                startActivity(i1);
                                dialog1Fragment.dismiss();
                                break;
                        }
                        return null;
                    }).show();
        });
    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void setCampaign(Campaign campaign) {
        GlideApp.with(this).load(campaign.imageCover)
                .error(R.drawable.splash_screen_background)
                .into(campaignImg);

        campaignTitle.setText(campaign.titleEn);
        campaignDayLeft.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(getBaseContext(), R.drawable.ic_time_white), null, null, null);
        campaignDayLeft.setText(new StringBuilder().append(campaign.daysLeft).append(" ").append(getString(R.string.days_left_value)).toString());

        campaignHostedBy.setText(getString(R.string.hosted_by_value, campaign.business.fullName));

        // initializing the pager fragment

        InnerCampaignFragmentPagerAdapter innerCampaignFragmentPagerAdapter = new InnerCampaignFragmentPagerAdapter(getSupportFragmentManager()
                , getFragmentPagerFragment(campaign)
                , getFragmentTitles(campaign.status, campaign.photosCount));
        campaignViewPager.setAdapter(innerCampaignFragmentPagerAdapter);
        if (campaign.status == CAMPAIGN_STATUS_APPROVED || campaign.status == CAMPAIGN_STATUS_RUNNING) {
            uploadCampaignBtn.setVisibility(View.VISIBLE);
        } else {
            uploadCampaignBtn.setVisibility(View.GONE);
        }
        campaignTabs.setupWithViewPager(campaignViewPager);
    }

    private List<Fragment> getFragmentPagerFragment(Campaign campaign) {
        int status = campaign.status;
        List<Fragment> fragmentList = new ArrayList<>();
        CampaignInnerMissionFragment campaignInnerMissionFragment = CampaignInnerMissionFragment.getInstance(campaign);
        fragmentList.add(campaignInnerMissionFragment);
        fragmentList.add(CampaignInnerPhotosFragment.getInstance(campaignId, status != CAMPAIGN_STATUS_APPROVED && status != CAMPAIGN_STATUS_RUNNING));
        return fragmentList;
    }

    private List<String> getFragmentTitles(int status, int photosCount) {
        List<String> fragmentList = new ArrayList<String>();
        fragmentList.add(getResources().getString(R.string.details));
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
            intent.putExtra(AllPhotographerPhotosActivity.CAMPAIGN_ID, String.valueOf(campaignId));
            startActivity(intent);
        });
        builder.show();
    }

//    public interface OnMissionCampaignDataRecived {
//        void onCampaignDescription(String desc);
//    }
}
