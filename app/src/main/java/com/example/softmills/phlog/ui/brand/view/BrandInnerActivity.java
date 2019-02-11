package com.example.softmills.phlog.ui.brand.view;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.base.commonmodel.Business;
import com.example.softmills.phlog.ui.brand.presenter.BrandInnerDataPresenterImpl;
import com.example.softmills.phlog.ui.brand.presenter.BrandInnerPresenter;

import io.reactivex.annotations.NonNull;

/**
 * Created by abdalla_maged on 11/12/2018.
 */
public class BrandInnerActivity extends BaseActivity implements BrandInnerActivityView {

    public static String BRAND_ID = "brand_id";
    private Business currentBrand;
    private FrameLayout brandHeaderImg;
    private ImageView brandIconImg;
    private Button followBrandBtn;
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
        followBrandBtn = findViewById(R.id.follow_brand_btn);
    }

    @Override
    public void initPresenter() {
        brandInnerPresenter = new BrandInnerDataPresenterImpl(getBaseContext(), this);
    }

    @Override
    public void viewInnerBrandData(Business brand) {
        try {
            this.currentBrand = brand;
            GlideApp.with(this)
                    .load(brand.imageCover)
                    .placeholder(R.drawable.default_place_holder)
                    .error(R.drawable.default_error_img)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, Transition<? super Drawable> transition) {
                            brandHeaderImg.setBackground(resource);
                        }
                    });

            GlideApp.with(this).load(brand.thumbnail)
                    .placeholder(R.drawable.default_place_holder)
                    .error(R.drawable.default_error_img)
                    .apply(RequestOptions.circleCropTransform())
                    .into(brandIconImg);


            if (brand.fullName != null) {
                brandName.setText(brand.fullName);
                aboutBrand.setText(new StringBuilder().append(getResources().getString(R.string.about_brand)).append(" : ").append(brand.fullName).toString());
            }


            brandNumFollowers.setText(new StringBuilder().append(brand.followersCount).append(" ").append(getResources().getString(R.string.followers)).toString());

//        if (brand.industry != null) {
            brandType.setText("Brand Industry");
//        }
            
            if (brand.isBrandText != null) {
                brandData.setText(brand.description);
            }
            if (brand.website != null) {
                brandWebsite.setText(brand.website);
            }
            if (brand.isFollow) {
                followBrandBtn.setText(getResources().getString(R.string.following));
            } else {
                followBrandBtn.setText(getResources().getString(R.string.follow));
            }
//        if (brand.email != null) {
//            brandWebsite.setText(brand.email);
//
//        }
        }catch (Exception e){
            Log.e(BrandInnerActivity.class.getSimpleName(),"Error =====>"+e.getMessage());
        }
    }

    private void intiListeners() {
        followBrandBtn.setOnClickListener(v -> {
            if (currentBrand != null){
                if(currentBrand.isFollow){
                    brandInnerPresenter.unFollowBrand(String.valueOf(currentBrand.id));
                }else {
                    brandInnerPresenter.followBrand(String.valueOf(currentBrand.id));
                }

            }
        });

        brandCampaign.setOnClickListener(v->{
            Intent intent=new Intent(this,BrandCampaignsActivity.class);
            intent.putExtra(BrandCampaignsActivity.BRAND_ID,currentBrand.id);
            startActivity(intent);
        });
    }

    @Override
    public void viwBrandFollowedState(Boolean state) {
        if (state) {
            followBrandBtn.setText(getResources().getString(R.string.following));
        } else {
            followBrandBtn.setText(getResources().getString(R.string.follow));
        }
    }

    @Override
    public void viewInnerBrandProgressBar(Boolean state) {
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
