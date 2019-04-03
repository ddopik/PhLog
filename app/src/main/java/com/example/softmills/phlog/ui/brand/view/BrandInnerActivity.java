package com.example.softmills.phlog.ui.brand.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.support.v7.content.res.AppCompatResources;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.base.commonmodel.Business;
import com.example.softmills.phlog.ui.brand.presenter.BrandInnerDataPresenterImpl;
import com.example.softmills.phlog.ui.brand.presenter.BrandInnerPresenter;
import com.example.softmills.phlog.ui.search.view.brand.view.BrandSearchFragment;
import com.o_bdreldin.loadingbutton.LoadingButton;

/**
 * Created by abdalla_maged on 11/12/2018.
 */
public class BrandInnerActivity extends BaseActivity implements BrandInnerActivityView {

    public static String BRAND_ID = "brand_id";
    private Toolbar brandProfileToolBar;
    private AppBarLayout mAppBarLayout;
    private CollapsingToolbarLayout brandProfileCollapsingToolbarLayout;
    private ImageButton backBtn;
    private Business currentBrand;
    private ImageView brandHeaderImg;
    private ImageView brandIconImg;
    private LoadingButton followBrandBtn;
    private TextView brandName, brandNumFollowers, brandType, aboutBrand, brandData, brandWebsite, brandMail, brandCampaign, brandProfileToolbarFollow, brandProfileToolbarTitle;
    private BrandInnerPresenter brandInnerPresenter;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner_brand);
        if (getIntent().getIntExtra(BRAND_ID, -1) >= 0) {
            initPresenter();
            initView();
            intiListeners();
            brandInnerPresenter.getBrandInnerData(getIntent().getIntExtra(BRAND_ID, 0));
        }
    }


    @Override
    public void initView() {

        mAppBarLayout = findViewById(R.id.brand_profile_appBar);
        brandProfileCollapsingToolbarLayout = findViewById(R.id.brand_profile_collapsing_layout);
        brandProfileToolBar = findViewById(R.id.brand_profile_toolbar);
        brandProfileToolbarTitle = findViewById(R.id.brand_profile_toolbar_title);
        brandProfileToolbarFollow = findViewById(R.id.tool_bar_follow_brand);
        backBtn = findViewById(R.id.back_btn);
        brandHeaderImg = findViewById(R.id.header_background_img);
        brandIconImg = findViewById(R.id.brand_img_icon);
        brandName = findViewById(R.id.brand_name);
        brandNumFollowers = findViewById(R.id.brand_num_followers);
        brandType = findViewById(R.id.brand_type);
        aboutBrand = findViewById(R.id.about_brand);
        brandData = findViewById(R.id.brand_data);
        brandWebsite = findViewById(R.id.brand_website);
        brandMail = findViewById(R.id.brand_mail);
        brandCampaign = findViewById(R.id.brand_campaign);
        progressBar = findViewById(R.id.brand_progress_bar);
        followBrandBtn = findViewById(R.id.follow_brand_btn);
        brandCampaign.setCompoundDrawablesWithIntrinsicBounds(null, null, AppCompatResources.getDrawable(getBaseContext(), R.drawable.ic_navigate_brand_forward_24dp), null);
    }

    @Override
    public void initPresenter() {
        brandInnerPresenter = new BrandInnerDataPresenterImpl(getBaseContext(), this);
    }

    @Override
    public void viewInnerBrandData(Business brand) {
        this.currentBrand = brand;
        GlideApp.with(this)
                .load(brand.imageCover)
                .placeholder(R.drawable.default_place_holder)
                .error(R.drawable.default_error_img)
                .into(brandHeaderImg);

        GlideApp.with(this).load(brand.thumbnail)
                .placeholder(R.drawable.default_place_holder)
                .error(R.drawable.default_error_img)
                .apply(RequestOptions.circleCropTransform())
                .into(brandIconImg);


        if (brand.fullName != null) {
            brandName.setText(brand.fullName);
            brandProfileToolbarTitle.setText(brand.fullName);
            aboutBrand.setText(new StringBuilder().append(getResources().getString(R.string.about_brand)).append(" : ").append(brand.fullName).toString());
        }


        brandNumFollowers.setText(new StringBuilder().append(brand.followersCount).append(" ").append(getResources().getString(R.string.followers)).toString());

        if (brand.industry != null) {
            brandType.setText(brand.industry.nameEn);
        }

        if (brand.isBrandText != null) {
            brandData.setText(brand.description);
        }
        if (brand.website != null) {
            brandWebsite.setText(brand.website);
        }
        if (brand.isFollow) {
            followBrandBtn.setText(getResources().getString(R.string.un_follow));
            brandProfileToolbarFollow.setText(getResources().getString(R.string.un_follow));
        } else {
            followBrandBtn.setText(getResources().getString(R.string.follow));
            brandProfileToolbarFollow.setText(getResources().getString(R.string.follow));
        }

        if (brand.website != null)
            brandWebsite.setText(brand.website);

        if (brand.email != null)
            brandMail.setText(brand.email);

//        if (brand.email != null) {
//            brandWebsite.setText(brand.email);
//
//        }
    }

    private void intiListeners() {
        followBrandBtn.setOnClickListener(v -> {
            followBrand();
        });

        brandProfileToolbarFollow.setOnClickListener(v -> {
            followBrand();
        });

        brandCampaign.setOnClickListener(v -> {
            Intent intent = new Intent(this, BrandCampaignsActivity.class);
            intent.putExtra(BrandCampaignsActivity.BRAND_ID, currentBrand.id);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
        brandProfileCollapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.black));
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                    brandProfileToolBar.setVisibility(View.VISIBLE);
                } else if (isShow) {
                    isShow = false;
                    brandProfileToolBar.setVisibility(View.GONE);
                }
            }
        });
        backBtn.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void viwBrandFollowedState(Boolean state) {
        followBrandBtn.setLoading(false);
        if (state) {
            currentBrand.isFollow = true;
            followBrandBtn.setText(getString(R.string.un_follow));
            brandProfileToolbarFollow.setText(getString(R.string.un_follow));
            currentBrand.followersCount++;
        } else {
            currentBrand.isFollow = false;
            followBrandBtn.setText(getResources().getString(R.string.follow));
            brandProfileToolbarFollow.setText(getResources().getString(R.string.follow));
            currentBrand.followersCount--;
        }
        brandNumFollowers.setText(new StringBuilder().append(currentBrand.followersCount).append(" ").append(getResources().getString(R.string.followers)).toString());
        Intent data = new Intent();
        data.putExtra(BrandSearchFragment.BRAND, currentBrand);
        setResult(RESULT_OK, data);
    }

    @Override
    public void viewInnerBrandProgressBar(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }


    private void followBrand() {
        if (currentBrand != null) {
            followBrandBtn.setLoading(true);
            if (currentBrand.isFollow) {
                brandInnerPresenter.unFollowBrand(String.valueOf(currentBrand.id));
            } else {
                brandInnerPresenter.followBrand(String.valueOf(currentBrand.id));
            }

        }
    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }
}
