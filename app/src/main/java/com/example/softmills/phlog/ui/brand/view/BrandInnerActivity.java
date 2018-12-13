package com.example.softmills.phlog.ui.brand.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.base.commonmodel.Brand;
import com.example.softmills.phlog.ui.brand.model.BrandInnerData;
import com.example.softmills.phlog.ui.brand.presenter.BrandInnerDataPresenterImpl;
import com.example.softmills.phlog.ui.brand.presenter.BrandInnerPresenter;

import io.reactivex.annotations.NonNull;

/**
 * Created by abdalla_maged on 11/12/2018.
 */
public class BrandInnerActivity extends BaseActivity implements BrandInnerActivityView {

    public static String BRAND_ID="brand_id";
    private FrameLayout brandHeaderImg;
    private ImageView brandIconImg;
    private TextView brandName, brandNumFollowers, brandType, aboutBrand, brandData, brandWebsite, brandMail, brandCampaign;
    private BrandInnerPresenter brandInnerPresenter;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner_brand);
        if (getIntent().getStringExtra(BRAND_ID) != null) {
            initPresenter();
            initView();
            intiListeners();
            brandInnerPresenter.getBrandInnerData(getIntent().getStringExtra(BRAND_ID));
        }
    }


    @Override
    public void initView() {

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

    }

    @Override
    public void initPresenter() {
        brandInnerPresenter = new BrandInnerDataPresenterImpl(getBaseContext(), this);
    }

    @Override
    public void viewInnerBrandData(Brand brand) {

        GlideApp.with(this)
                .load(brand.imageCover)
                .placeholder(R.drawable.default_photographer_profile)
                .error(R.drawable.default_photographer_profile)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        // log exception
                        Log.e("TAG", "Error loading image", e);
                        return false; // important to return false so the error placeholder can be placed
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, Transition<? super Drawable> transition) {
                        brandHeaderImg.setBackground(resource);
                    }
                });

//        GlideApp.with(this)
//                .load(brandInnerData.coverImage)
//                .placeholder(R.drawable.default_photographer_profile)
//                .error(R.drawable.default_photographer_profile)
//                .apply(RequestOptions.circleCropTransform())
//                .into(brandIconImg);

        GlideApp.with(this).load(brand.thumbnail).apply(RequestOptions.circleCropTransform()).into(brandIconImg);


        if (brand.fullName != null) {
            brandName.setText(brand.fullName);
            aboutBrand.setText(new StringBuilder().append(getResources().getString(R.string.about_brand)).append(" : ").append(brand.fullName).toString());
        }
        if (brand.followersCount != null) {
            brandNumFollowers.setText(new StringBuilder().append(brand.followersCount).append(" ").append(getResources().getString(R.string.followers)).toString());
        }
//        if (brand.industry != null) {
            brandType.setText("Brand Industry");
//        }
        if (brand.isBrandText != null) {
            brandData.setText(brand.description);
        }
        if (brand.website != null) {
            brandWebsite.setText(brand.website);
        }
//        if (brand.email != null) {
//            brandWebsite.setText(brand.email);
//
//        }

    }

    private void intiListeners() {


    }


    @Override
    public void viewInnerBrandProgressBar(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
