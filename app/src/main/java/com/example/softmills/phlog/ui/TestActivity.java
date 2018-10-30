package com.example.softmills.phlog.ui;


import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.BitmapUtils;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.ui.uploadimage.view.EditPickedImageFragment;
import com.example.softmills.phlog.ui.uploadimage.view.fillters.FiltersListFragment;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 10/15/2018.
 */
public class TestActivity extends BaseActivity  {



    // load native image filters library
    static {
        System.loadLibrary("NativeImageProcessor");
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initView();


    }
    @Override
    public void initView() {

    }

    @Override
    public void initPresenter() {

    }





}

